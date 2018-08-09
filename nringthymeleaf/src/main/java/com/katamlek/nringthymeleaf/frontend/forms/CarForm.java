package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CarGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.CarNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
import com.katamlek.nringthymeleaf.repositories.PriceListCarRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;

/**
 * Used to enter a new car data/ edit the existing car data.
 */

@SpringView
@UIScope
public class CarForm extends VerticalLayout implements View {

    // The constructor
    private CarRepository carRepository;
    private CarNoteRepository carNoteRepository;
    private PriceListCarRepository priceListCarRepository;
    private NavigationManager navigationManager;

    public CarForm(CarRepository carRepository, CarNoteRepository carNoteRepository, NavigationManager navigationManager, PriceListCarRepository priceListCarRepository) {
        this.carRepository = carRepository;
        this.carNoteRepository = carNoteRepository;
        this.priceListCarRepository = priceListCarRepository;
        this.navigationManager = navigationManager;
        addComponent(buildCarForm());
    }

    // Window label & buttons
    private Label carFormL = new Label("Car Form");
    private Button saveB;
    private Button cancelB;

    // Section fields
    // The details section
    private TextField modelTF;
    private TextField plateTF;
    ComboBox<CarColor> carColorCB;
    private TextField lastMileageTF;
    private DateField firstBookingDF;
    private DateField lastBookingDF;
    private RadioButtonGroup<MileageType> mileageTypeRBG;
    private RadioButtonGroup<CarFleet> carFleetRBG;
    private DateField nextTUV;
    private TextField nextService;

    // The status section
    private ComboBox<CarStatus> statusCB;
    private TextField locationTF;
    private Grid<CarNote> carNoteG; // where isHistory false
    private Button addNoteB; // other than history note - isHistory false

    // History section
    private Grid<CarNote> carHistoryNoteG; // only isHistory true
    private Button addHistoryNoteB;

    // Pricing section
    private Grid<PriceListCar> pricingG;
    private Button addPricingItemB;

    // Binder
    private Binder<Car> carBinder;

    // Build section layouts methods
    // Details
    public HorizontalLayout buildDetailsSection() {

        modelTF = new TextField("Model");
        plateTF = new TextField("Plate");
        carColorCB = new ComboBox<CarColor>();
        carColorCB.setItems(CarColor.values());
        carColorCB.setCaption("Color");
        VerticalLayout leftPane = new VerticalLayout(modelTF, plateTF, carColorCB);

        lastMileageTF = new TextField("Last recorded mileage");
        firstBookingDF = new DateField("First booking");
        lastBookingDF = new DateField("Last booking");
        VerticalLayout middlePane = new VerticalLayout(lastMileageTF, firstBookingDF, lastBookingDF);

        mileageTypeRBG = new RadioButtonGroup<>("Mileage type", DataProvider.ofItems(MileageType.values()));
        carFleetRBG = new RadioButtonGroup<>("Fleet", DataProvider.ofItems(CarFleet.values()));
        nextTUV = new DateField("Next TUV");
        nextService = new TextField("Next service at");
        VerticalLayout rightPane = new VerticalLayout(mileageTypeRBG, carFleetRBG, nextTUV, nextService);

        HorizontalLayout detailsHL = new HorizontalLayout(leftPane, middlePane, rightPane);
        detailsHL.setCaption("Car Details");

        return detailsHL;

    }

    // Status
    public VerticalLayout buildStatusSection() {
        VerticalLayout statusVL = new VerticalLayout();

        statusCB = new ComboBox<>();
        statusCB.setItems(CarStatus.values());
        statusCB.setCaption("Car status");

        locationTF = new TextField("Location"); //todo ask Jono if this should be LocationDefinition

        carNoteG = new Grid<>(CarNote.class);

        carNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), false));

        carNoteG.setColumns("user", "enteredOn", "text");
        carNoteG.setColumnOrder("user", "enteredOn", "text");
        carNoteG.getColumns().forEach(column -> column.setSortable(true));
        carNoteG.setColumnReorderingAllowed(true);
        carNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing - of non-history notes only
        // carNoteG.getEditor().setEnabled(true);

        // extra button delete for non-history entries only

        carNoteG.addComponentColumn(this::deleteCarNote);

        carNoteG.setSizeFull();

        statusVL.addComponents(statusCB, locationTF, carNoteG);
        statusVL.setCaption("Car Status");
        return statusVL;

    }

    // History
    public VerticalLayout buildHistorySection() {
        VerticalLayout historyVL = new VerticalLayout();

        carHistoryNoteG = new Grid<>(CarNote.class);
        carHistoryNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), true));

        carHistoryNoteG.setColumns("user", "enteredOn", "text", "carMileageOut", "carMileageIn", "carMileageTotalKM", "carMileageTotalMIL");
        carHistoryNoteG.setColumnOrder("user", "enteredOn", "text", "carMileageOut", "carMileageIn", "carMileageTotalKM", "carMileageTotalMIL");
        carHistoryNoteG.getColumns().forEach(column -> column.setSortable(true));
        carHistoryNoteG.setColumnReorderingAllowed(true);
        carHistoryNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);
        // no inline editing - it's history
        // no buttons delete, edit - it's history entries
        carNoteG.setSizeFull();

        addHistoryNoteB = new Button("Add history note");
        addHistoryNoteB.addClickListener(e -> Notification.show("I can't do this yet")); //todo


        historyVL.addComponents(carHistoryNoteG, addHistoryNoteB);
        historyVL.setCaption("Car History");
        return historyVL;
    }

    public VerticalLayout buildPricingSection() {
        VerticalLayout pricingVL = new VerticalLayout();
        pricingG = new Grid<>(PriceListCar.class);

        pricingG.setItems(priceListCarRepository.findDistinctByCar(carBinder.getBean()));

        pricingG.setColumns("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "carPricingGroup");
        pricingG.setColumnOrder("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "carPricingGroup");
        pricingG.getColumns().forEach(column -> column.setSortable(true));
        pricingG.setColumnReorderingAllowed(true);
        pricingG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // carNoteG.getEditor().setEnabled(true);

        // extra button delete--nope, this is price list, sorry

        pricingG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Please be patient, will open the form soon.");
                // todo goto priceList form(car) when the form is ready;
            }
        });

        pricingG.setSizeFull();

        addPricingItemB = new Button("Add pricing item");
        addPricingItemB.addClickListener(e -> Notification.show("Can't do this yet, sorry")); //todo

        pricingVL.addComponents(pricingG, addPricingItemB);
        return pricingVL;
    }

    // Put all together
    public VerticalLayout buildCarForm() {
        VerticalLayout carForm = new VerticalLayout();
        carForm.addComponents(carFormL, buildDetailsSection(), buildStatusSection(), buildHistorySection());

        // The binder
        carBinder = new Binder(Car.class); //todo define fields

        // Form buttons
        HorizontalLayout buttonsHL = new HorizontalLayout();

        cancelB = new Button("Cancel");
        cancelB.setDescription("Caution! Your data will be lost!");
        cancelB.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        cancelB.setIcon(VaadinIcons.ERASER);
        cancelB.addClickListener(e -> {
            //Confirmation popup
            Window window = new Window("Do you really want to drop the changes?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Drop the form and take me back");
            yesButton.addClickListener(event1 -> {
                window.close();
                navigationManager.navigateTo(BookingGridView.class);
            });

            Button noButton = new Button("Let's keep on working");
            noButton.addClickListener(event2 -> {
                window.close();
            });

            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
            confirmationVL.addComponent(buttonsLayout);

            window.setContent(confirmationVL);

            window.center();
            UI.getCurrent().addWindow(window);

        });

        saveB = new Button("Save");

        saveB.addClickListener(e -> {
            try {
                carRepository.save(carBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                carBinder.getBean().setUnderEditing(false);

                Notification.show("I saved your data.");
            }
        });

        return carForm;

    }


    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String carId = event.getParameters();
        if ("".equals(carId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(carId));
        }
    }

    // Called when user enters view from the list or adding a new car
    public void enterView(Long id) {
        Car car;
        if (id == null) {
            // New
            car = new Car();

            car.setUnderEditing(true);
            // todo more setters
        } else {
            car = carRepository.findById(id).get();
            if (car.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
                navigationManager.navigateTo(CarGridView.class); //todo navigate to the previous view? are there more entry points?
            } else {
                car.setUnderEditing(true);
                if (car == null) {
                    showNotFound();
                    return;
                }
            }
        }
        carBinder.setBean(car);
        // todo ??.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Car not found"));
    }

    // Helpers

    private Button deleteCarNote(CarNote carNote) {
        Button deleteCNButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCNButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCNButton.addClickListener(e -> {
            if (carNote.isUnderEditing()) {
                Notification.show("Someone's working with this note. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this note?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    carNoteRepository.delete(carNote);
                    carNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), false));
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);
            }
        });
        return deleteCNButton;
    }

}
