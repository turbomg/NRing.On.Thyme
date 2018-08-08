package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.CarGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.CarNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
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
//    private CarPricingRepository carPricingRepository;
    private NavigationManager navigationManager;

    public CarForm(CarRepository carRepository, CarNoteRepository carNoteRepository, NavigationManager navigationManager) {
        this.carRepository = carRepository;
        this.carNoteRepository = carNoteRepository;
    //    this.carPricingRepository = carPricingRepository;
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
//todo    private Grid<CarPricing> pricingG;
    private Button addPricingItemB;

    // Binder
    private Binder<Car> carBinder;

    // Build section layouts methods
    // Details
    public HorizontalLayout buildDetailsSection() {

        modelTF = new TextField("Model");
        plateTF = new TextField("Plate"); //todo remember to check if not already in the db
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
        //todo get the id parametr and pass to the method -- not sure how :)
// todo         carNoteG.setItems(Lists.newArrayList(carNoteRepository.findByCarIdAndAndHistoryNote(1l, false)));


        carNoteG.setColumns("user", "enteredOn", "text");
        carNoteG.setColumnOrder("user", "enteredOn", "text");
        carNoteG.getColumns().forEach(column -> column.setSortable(true));
        carNoteG.setColumnReorderingAllowed(true);
        carNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing - of non-history notes only
        carNoteG.getEditor().setEnabled(true); //todo lock user and enteredOn?

        // extra buttons delete, edit - for non-history entries only
        carNoteG.addColumn(carNote -> "Edit", new ButtonRenderer(clickEvent -> {
            //todo navigator
        }));

        carNoteG.addColumn(carNote -> "Delete", new ButtonRenderer(clickEvent -> {
            //todo check if works, switch to id?
            carNoteRepository.delete((CarNote) clickEvent.getItem());
        }));

        carNoteG.setSizeFull();

        statusVL.addComponents(statusCB, locationTF, carNoteG);
        statusVL.setCaption("Car Status");
        return statusVL;

    }

    // History
    public VerticalLayout buildHistorySection() {
        VerticalLayout historyVL = new VerticalLayout();

        carHistoryNoteG = new Grid<>(CarNote.class);
        //todo get the id parametr and pass to the method -- not sure how :)
     //todo   carHistoryNoteG.setItems(Lists.newArrayList(carNoteRepository.findByCarIdAndAndHistoryNote(1l, true)));

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

    // todo Pricing
//    public VerticalLayout buildPricingSection() {
//        VerticalLayout pricingVL = new VerticalLayout();
//        pricingG = new Grid<>(CarPricing.class);
//        // todo pass car id to the below method - ask Darek how
//        pricingG.setItems(Lists.newArrayList(carPricingRepository.findByCarId(34343l)));
//        pricingG.setColumns("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "carPricingGroup");
//        pricingG.setColumnOrder("description", "defaultNote", "startPrice", "startKM", "per10KM", "instruction", "carPricingGroup");
//        pricingG.getColumns().forEach(column -> column.setSortable(true));
//        pricingG.setColumnReorderingAllowed(true);
//        pricingG.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // allow inline editing - of non-history notes only
//        carNoteG.getEditor().setEnabled(true); //todo lock some columns?
//
//        // extra buttons delete, edit - for non-history entries only
//        carNoteG.addColumn(carNote -> "Edit", new ButtonRenderer(clickEvent -> {
//            //todo navigator
//        }));
//
//        carNoteG.addColumn(carNote -> "Delete", new ButtonRenderer(clickEvent -> {
//            //todo check if works, switch to id?
//            carNoteRepository.delete((CarNote) clickEvent.getItem());
//        })); //todo enable delete?
//
//        pricingG.setSizeFull();
//
//        addPricingItemB = new Button("Add pricing item");
//        addPricingItemB.addClickListener(e -> Notification.show("Can't do this yet, sorry")); //todo
//
//        pricingVL.addComponents(pricingG, addPricingItemB);
//        return pricingVL;
//    }

    // Put all together
    public VerticalLayout buildCarForm() {
        // todo I guess these methods should receive the car id and work accordingly... ask Darek
        VerticalLayout carForm = new VerticalLayout();
        carForm.addComponents(carFormL, buildDetailsSection(), buildStatusSection(), buildHistorySection());

        // The binder
        carBinder = new Binder(Car.class); //todo

        // Form buttons
        HorizontalLayout buttonsHL = new HorizontalLayout();

        cancelB = new Button("Cancel");
        cancelB.addClickListener(e -> navigationManager.navigateTo(CarGridView.class));

        saveB = new Button("Save");

        saveB.addClickListener(e -> {
            try {
                carRepository.save(carBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                carBinder.getBean().setUnderEditing(false);
                Notification.show("Got it"); //for testing purposes
            }
        });

        return carForm;

    }


    // enter and on Enter

    // Form open processing (new / edit)
    // todo I should pass the id here I guess...
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String carId = event.getParameters();
        if ("".equals(carId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(carId));
        }
    }

    // Called when user enters view from the list or adding a new event
    //todo set underEditing flag: true - the form won't open, false - let's go;; after save() set it to false;; update the domain
    public void enterView(Long id) {
        Car car;
        if (id == null) {
            // New
            car = new Car();
            // todo setters
        } else {
            //todo check if not under editing -- guess need to save the flag in the DB ?
            //todo checked the flags on related data like internal info etc
            car = carRepository.findById(id).get();
            car.setUnderEditing(true);
            if (car == null) {
                showNotFound();
                return;
            }
        }
//todo         eventBinder.setBean(event);
//        eventNameTF.focus();

    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Order not found"));
    }

}
