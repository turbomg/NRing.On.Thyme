package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CarGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.CarNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
import com.katamlek.nringthymeleaf.repositories.LocationDefinitionRepository;
import com.katamlek.nringthymeleaf.repositories.PriceListCarRepository;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToBigDecimalConverter;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToIntegerConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
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
    private LocationDefinitionRepository locationDefinitionRepository;
    private NavigationManager navigationManager;

    public CarForm(CarRepository carRepository, CarNoteRepository carNoteRepository, LocationDefinitionRepository locationDefinitionRepository, NavigationManager navigationManager, PriceListCarRepository priceListCarRepository) {
        this.carRepository = carRepository;
        this.carNoteRepository = carNoteRepository;
        this.priceListCarRepository = priceListCarRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.navigationManager = navigationManager;
        addComponent(buildCarForm());
        setMargin(false);
    }

    // Window label & buttons
    private Label carFormL = new Label("Car Form");
    private Button saveB;
    private Button cancelB;

    // Section fields
    // The details section
    private TextField modelTF;
    private TextField plateTF;
    private TextField vinNumberTF;
    ComboBox<CarColor> carColorCB;
    private DateField firstRegistrationDF;
    private TextField lastMileageTF;
    private DateField firstBookingDF;
    private DateField lastBookingDF;
    private TextField carValueTF;
    private RadioButtonGroup<MileageType> mileageTypeRBG;
    private RadioButtonGroup<CarFleet> carFleetRBG;
    private DateField nextTUV;
    private TextField nextServiceAtKm;

    // The status section
    private ComboBox<CarStatus> statusCB;
    private ComboBox<LocationDefinition> locationCB;
    private Grid<CarNote> carNoteG; // where isHistory false
    private Button addNoteB; // other than history note - isHistory false
    private VerticalLayout carNotesVL = buildCarNoteForm();
    private TextField noteTF;
    private DateField noteDateDF;

    // History section
    private Grid<CarNote> carHistoryNoteG; // only isHistory true
    private Button addHistoryNoteB;

    // Pricing section
    private Grid<PriceListCar> pricingG;
    private Button addPricingItemB;

    // Binder
    private Binder<Car> carBinder = new Binder<>(Car.class);

    // Build section layouts methods
    // Details
    public HorizontalLayout buildDetailsSection() {
        modelTF = new TextField("Model");
        plateTF = new TextField("Plate");
        vinNumberTF = new TextField("VIN");
        carColorCB = new ComboBox<CarColor>();
        carColorCB.setItems(CarColor.values());
        carColorCB.setCaption("Color");
        // todo set renderer when graphics ready
        VerticalLayout leftPane = new VerticalLayout(modelTF, plateTF, vinNumberTF, carColorCB);

        firstRegistrationDF = new DateField("First registration");
        lastMileageTF = new TextField("Last recorded mileage");
        firstBookingDF = new DateField("First booking");
        lastBookingDF = new DateField("Last booking");
        carValueTF = new TextField("Car value");
        VerticalLayout middlePane = new VerticalLayout(firstRegistrationDF, lastMileageTF, firstBookingDF, lastBookingDF, carValueTF);

        mileageTypeRBG = new RadioButtonGroup<>("Mileage type", DataProvider.ofItems(MileageType.values()));
        carFleetRBG = new RadioButtonGroup<>("Fleet", DataProvider.ofItems(CarFleet.values()));
        nextTUV = new DateField("Next TUV");
        nextServiceAtKm = new TextField("Next service at");
        VerticalLayout rightPane = new VerticalLayout(mileageTypeRBG, carFleetRBG, nextTUV, nextServiceAtKm);

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

        locationCB = new ComboBox<>("Location");
        locationCB.setItemCaptionGenerator(e -> e.getLocationName());
        locationCB.setItems(Lists.newArrayList(locationDefinitionRepository.findAll()));

        carNoteG = new Grid<>(CarNote.class);
        carNoteG.setHeightByRows(4);
        carNoteG.setCaption("Car notes");

        carNoteG.setColumns("user", "enteredOn", "text");
        carNoteG.setColumnOrder("enteredOn", "user", "text");
        carNoteG.getColumns().forEach(column -> column.setSortable(true));
        carNoteG.setColumnReorderingAllowed(true);
        carNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing - of non-history notes only
        // carNoteG.getEditor().setEnabled(true);

        // extra button delete for non-history entries only

    //    carNoteG.addComponentColumn(this::deleteCarNote);

        carNoteG.setSizeFull();

        addNoteB = new Button("Add a note");
        addNoteB.addClickListener(e -> {
            carNotesVL.setVisible(true);
            addNoteB.setVisible(false);
        });


        statusVL.addComponents(statusCB, locationCB, carNoteG, addNoteB, carNotesVL);
        statusVL.setCaption("Car status");
        return statusVL;

    }

    // History
    public VerticalLayout buildHistorySection() {
        VerticalLayout historyVL = new VerticalLayout();

        carHistoryNoteG = new Grid<>(CarNote.class);
        carHistoryNoteG.setCaption("Car history");
        carHistoryNoteG.setHeightByRows(4);

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
        return historyVL;

        // todo if manual input is enabled, add noteVL
    }

    public VerticalLayout buildPricingSection() {
        VerticalLayout pricingVL = new VerticalLayout();
        pricingG = new Grid<>(PriceListCar.class);
        pricingG.setCaption("Car pricing");
        pricingG.setHeightByRows(4);

        pricingG.setColumns("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "pricingGroup");
        pricingG.setColumnOrder("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "pricingGroup");
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
        carForm.addComponents(carFormL, buildDetailsSection(), buildStatusSection(), buildHistorySection(), buildPricingSection());

        // Bindings
        // For the car
        carBinder.bind(modelTF, "model");
        carBinder.bind(plateTF, "plate");
        carBinder.bind(vinNumberTF, "carVin");
        carBinder.bind(carColorCB, "carColor");
        carBinder.bind(firstRegistrationDF, "carFirstRegistrationDate");
        carBinder.forField(lastMileageTF)
                .withConverter(new CustomStringToIntegerConverter("Enter a number please."))
                .bind(Car::getLastMileage, Car::setLastMileage);
        carBinder.bind(firstBookingDF, "firstBooking");
        carBinder.bind(lastBookingDF, "lastBooking");
        carBinder.forField(carValueTF)
                .withConverter(new CustomStringToBigDecimalConverter("Please enter a number."))
                .bind(Car::getCarValueFromAdac, Car::setCarValueFromAdac);
        carBinder.bind(mileageTypeRBG, "mileageType");
        carBinder.bind(carFleetRBG, "carFleet");
        carBinder.bind(nextTUV, "nextTUV");
        carBinder.forField(nextServiceAtKm)
                .withConverter(new CustomStringToIntegerConverter("Please enter a number."))
                .bind(Car::getNextServiceAtKm, Car::setNextServiceAtKm);

        carBinder.bind(statusCB, "carStatus");
        carBinder.bind(locationCB, "currentLocation");

        // todo For the car history - if we want
        // todo For the pricing - in stage 2

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

            carNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), false));
            carHistoryNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), true));
            pricingG.setItems(priceListCarRepository.findDistinctByCar(carBinder.getBean()));


        }
        carBinder.setBean(car);
        // todo ??.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Car not found"));
    }

    // Mini - layouts with notes
    // Event note form
    public VerticalLayout buildCarNoteForm() {
        VerticalLayout carNoteVL = new VerticalLayout();
        carNoteVL.setCaption("Enter the note");

        /**
         * I do not allow to edit notes deliberately - too much a hassle, no real need to do it.
         * If needed, I can get back to this form and implement editing.
         */

        // todo null newCarNote as adding another still works on the same instance
//        moved down EventNote newEventNote = new EventNote();

        // Fields
        noteDateDF = new DateField("Note date");
        noteTF = new TextField("Text");

        HorizontalLayout fieldsHL = new HorizontalLayout(noteDateDF, noteTF);

        // Separate binder
        Binder<CarNote> carNoteBinder = new Binder<>(CarNote.class);
        carNoteBinder.forField(noteDateDF).withConverter(new LocalDateToDateConverter()).bind(CarNote::getEnteredOn, CarNote::setEnteredOn);
        carNoteBinder.bind(noteTF, "text");

        CarNote newCarNote = new CarNote();
        carNoteBinder.setBean(newCarNote);

        // Buttons
        Button cancelNote = new Button("Cancel");
        cancelNote.setDescription("Your data will be lost!");
        cancelNote.addClickListener(e -> {
            this.carNotesVL.setVisible(false);
            this.addNoteB.setVisible(true);
            noteDateDF.clear();
            noteTF.clear();
        });

        Button saveNote = new Button("Save");
        saveNote.addClickListener(e -> {
            carBinder.getBean().addCarNote(carNoteBinder.getBean());

            this.carNotesVL.setVisible(false);
            this.addNoteB.setVisible(true);
            carRepository.save(carBinder.getBean());
            noteDateDF.clear();
            noteTF.clear();
            carNoteG.setItems(carNoteRepository.findDistinctByCarAndHistoryNote(carBinder.getBean(), false));
        });

        HorizontalLayout documentButtonsHL = new HorizontalLayout(cancelNote, saveNote);

        carNoteVL.addComponents(fieldsHL, documentButtonsHL);

        carNoteVL.setVisible(false);
        return carNoteVL;
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
