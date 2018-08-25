//package com.katamlek.nringthymeleaf.frontend.forms;
//
//import com.katamlek.nringthymeleaf.domain.*;
//import com.katamlek.nringthymeleaf.repositories.*;
//import com.vaadin.data.Binder;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.SpringView;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.*;
//import org.assertj.core.util.Lists;
//
//import javax.xml.soap.Text;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@UIScope
//@SpringView
//public class BookingPackageItemForm extends VerticalLayout implements View {
//    private PriceListEventRepository priceListEventRepository;
//    private PriceListOtherRepository priceListOtherRepository;
//    private PriceListCarRepository priceListCarRepository;
//    private PriceListRepository priceListRepository;
//    private EventRepository eventRepository;
//    private CarRepository carRepository;
//    private LocationDefinitionRepository locationDefinitionRepository;
//    private PricingGroupRepository pricingGroupRepository;
//    private BookingPackageItemRepository bookingPackageItemRepository;
//
//    //todo ??? How do I pick data from price list: from the superclass or children?
//
//    public BookingPackageItemForm(PriceListEventRepository priceListEventRepository, PriceListOtherRepository priceListOtherRepository,
//                                  PriceListCarRepository priceListCarRepository, PriceListRepository priceListRepository,
//                                  EventRepository eventRepository, CarRepository carRepository,
//                                  LocationDefinitionRepository locationDefinitionRepository, PricingGroupRepository pricingGroupRepository,
//                                  BookingPackageItemRepository bookingPackageItemRepository) {
//        this.priceListEventRepository = priceListEventRepository;
//        this.priceListOtherRepository = priceListOtherRepository;
//        this.priceListCarRepository = priceListCarRepository;
//        this.priceListRepository = priceListRepository;
//        this.eventRepository = eventRepository;
//        this.carRepository = carRepository;
//        this.locationDefinitionRepository = locationDefinitionRepository;
//        this.pricingGroupRepository = pricingGroupRepository;
//        this.bookingPackageItemRepository = bookingPackageItemRepository;
//
//        addComponent(new Label("Package item form"));
//        addComponent(buildBookingPackageItemLayout());
//        setMargin(false);
//    }
//
//    // Binder
//    private Binder itemBinder;
//
//    //todo binding, decision where to place it: on the form or on the popup
//
//    // Get the ofConcern, build window layout accordingly:
//
//    public VerticalLayout buildBookingPackageItemLayout() {
//        VerticalLayout packageItemVL = new VerticalLayout();
//        packageItemVL.setCaption("Package item data");
//
//        // First select the area of concern
//        ComboBox<OfConcern> concernsCB = new ComboBox<OfConcern>("Select the category:");
//        concernsCB.setItems(OfConcern.values());
//        concernsCB.addSelectionListener(e -> {
//
//            // If the ofConcern equals Event set content of the packageitemVL to:
//            if ((e.getSelectedItem().get()).equals(OfConcern.EVENT)) {
//                packageItemVL.addComponents(buildEventItemVL());
//            }
//            // If the ofConcern equals Other set content of the packageitemVL to:
//            else if ((e.getSelectedItem().get()).equals(OfConcern.OTHER)) {
//                packageItemVL.addComponents(buildOtherItemVL());
//            } else
//                // If the ofConcern equals Car set content of the packageitemVL to:
//                packageItemVL.addComponents(buildCarItemForm());
//        });
//
//        packageItemVL.addComponents(concernsCB);
//
//
//        setMargin(false);
//
//        return packageItemVL;
//    }
//
//    private VerticalLayout buildEventItemVL() {
//        VerticalLayout eventItemVL = new VerticalLayout();
//
//        // Pick the event
//
//        List<com.katamlek.nringthymeleaf.domain.Event> eventList = Lists.newArrayList(eventRepository.findAll());
//        ComboBox<com.katamlek.nringthymeleaf.domain.Event> eventsCB = new ComboBox<>("Pick the event");
//        eventsCB.setItems(eventList);
//        eventsCB.setItemCaptionGenerator(e -> e.getEventName());
//
//        // todo selectedItemListener
//
//        // These fields will be set automatically and locked
//        DateField eventDateDF = new DateField("Event date");
//        eventDateDF.setEnabled(false);
//        TextField eventStartTimeTF = new TextField("Event start time");
//        eventStartTimeTF.setEnabled(false);
//
//        TextField entryFeeTF = new TextField("Entry fee");
//      //  entryFeeTF.setValue("10"); //todo set the value
//        entryFeeTF.setEnabled(false);
//
//        TextField additionalDriverFeeTF = new TextField("Additional driver fee");
//        additionalDriverFeeTF.setValue("temp value"); //todo
//        additionalDriverFeeTF.setEnabled(false);
//
//        // In these you enter the quantities
//        TextField entryEventNumberTF = new TextField("Quantity");
//        TextField driverEventNumberTF = new TextField("Quantity");
//
//        // These are calculated automatically and locked
//        TextField eventTotalTF = new TextField("Total - event");
//        eventTotalTF.setEnabled(false);
////todo        BigDecimal eventTotal = BigDecimal.valueOf(Integer.valueOf(entryFeeTF.getValue()) * Integer.valueOf(entryEventNumberTF.getValue()));
////        eventTotalTF.setValue(String.valueOf(eventTotal));
//
//        TextField additionalDriverTotalTF = new TextField("Total - additional driver");
//        additionalDriverTotalTF.setEnabled(false);
////todo        BigDecimal additionalDriverTotal = BigDecimal.valueOf(Integer.valueOf(additionalDriverFeeTF.getValue()) * Integer.valueOf(driverEventNumberTF.getValue()));
////        additionalDriverTotalTF.setValue(String.valueOf(additionalDriverTotal));
//
//        // Some tweaking with the layouts
//        HorizontalLayout eventDataHL = new HorizontalLayout(eventsCB, eventDateDF, eventStartTimeTF);
//        HorizontalLayout eventEntryHL = new HorizontalLayout(entryFeeTF, entryEventNumberTF, eventTotalTF);
//        HorizontalLayout additionalEventDriverHL = new HorizontalLayout(additionalDriverFeeTF, driverEventNumberTF, additionalDriverTotalTF);
//        eventItemVL.addComponents(eventDataHL, eventEntryHL, additionalEventDriverHL);
//
//        // Discount - only a custom one
//        TextField discountEventPercentageTF = new TextField("Discount (%)");
//        TextField discountEventAmountTF = new TextField("Discount amount"); // enabled if someone wants to correct the % amount
//        //todo set the value of discount
//
//        // Again, tweak the layout
//        HorizontalLayout discountsEventHL = new HorizontalLayout(discountEventPercentageTF, discountEventAmountTF);
//        eventItemVL.addComponents(discountsEventHL);
//
//        // The last item is notes grid and a button after that
//        eventItemVL.addComponents(buildPackageItemNoteGrid()); // with the notes concerning the given package item
//
//        // These fields are not visible to the user and set automatically by the system
//        //todo at the binding process: unit of measure and so on
//        // todo at the save() stage check if there are still places free in the event
//
//        setMargin(false);
//        return eventItemVL;
//    }
//
//    private VerticalLayout buildOtherItemVL() {
//        VerticalLayout otherItemVL = new VerticalLayout();
//
//        List<PriceListOther> othersList = Lists.newArrayList(priceListOtherRepository.findAll());
//        ComboBox<PriceListOther> otherCB = new ComboBox<>("Pick the miscalenious");
//        otherCB.setItems(othersList);
//        otherCB.setItemCaptionGenerator(o -> o.getDescription());
//
//        // Not locked - event has a locked date, others don't
//        DateField otherForDateTF = new DateField();
//
//        // Pricing fields - disabled, derived from the price list
//        TextField unitPriceOtherTF = new TextField("Unit price" );
//        unitPriceOtherTF.setEnabled(false);
//        TextField unitOfMeasureOtherTF = new TextField("UOM");
//        unitOfMeasureOtherTF.setEnabled(false);
//
//        // Quantity
//        TextField quantityOtherTF = new TextField("Quantity");
//
//        // The total
//        BigDecimal otherTotal = BigDecimal.valueOf(Integer.valueOf(unitPriceOtherTF.getValue()) * Integer.valueOf(quantityOtherTF.getValue()));
//        TextField otherTotalTF = new TextField("Total");
//        otherTotalTF.setValue(String.valueOf(otherTotal));
//
//        // And the discounts if any
//        TextField discountPercentageOtherTF = new TextField("Discount (%)");
//        TextField discountAmountOtherTF = new TextField("Discount amount"); // enabled if someone wants to correct the % amount
//        //todo set the value of discount
//
//        // The notes grid
//        //todo see the note at buildPackageItemNoteGrid
//
//        // Tweaking with the layout
//        HorizontalLayout datesAndPricesOtherHL = new HorizontalLayout(otherForDateTF, unitPriceOtherTF, unitOfMeasureOtherTF, quantityOtherTF, otherTotalTF);
//        HorizontalLayout discountsOtherHL = new HorizontalLayout(discountPercentageOtherTF, discountAmountOtherTF);
//
//        otherItemVL.addComponents(otherCB, datesAndPricesOtherHL, discountsOtherHL);
//
//        setMargin(false);
//        return otherItemVL;
//    }
//
//    private VerticalLayout buildCarItemForm() {
//        VerticalLayout carItemVL = new VerticalLayout();
//
//        // Location, pricing group
//        ComboBox<LocationDefinition> carLocations = new ComboBox<>("Location");
//        carLocations.setItems(Lists.newArrayList(locationDefinitionRepository.findAll()));
//        carLocations.setItemCaptionGenerator(l -> l.getLocationName());
//
//        ComboBox<PricingGroup> carPricingGroupsCB = new ComboBox<>("Pricing group");
//        carPricingGroupsCB.setItems(Lists.newArrayList(pricingGroupRepository.findAll()));
//        carPricingGroupsCB.setItemCaptionGenerator(g -> g.getPricingGroupName());
//
//        HorizontalLayout locationPricingHL = new HorizontalLayout(carLocations, carPricingGroupsCB);
//
//        // Date, time, car, description
//        DateField carHireDate = new DateField("Date");
//        TextField carHireStartTime = new TextField("Start time");
//
//        ComboBox<Car> availableCarsCB = new ComboBox<>("Car"); //todo pick by car status FREE and not booked on carHireDate
//
//        ComboBox<PriceListCar> carDescriptionCB = new ComboBox<>("Description");
//        List<PriceListCar> priceListCars = Lists.newArrayList(priceListCarRepository.findAll());
//        carDescriptionCB.setItems(priceListCars);
//        carDescriptionCB.setItemCaptionGenerator(p -> p.getDescription());
//
//        HorizontalLayout dateTimeCarDescriptionHL = new HorizontalLayout(carHireDate, carHireStartTime, availableCarsCB, carDescriptionCB);
//
//        // Unit price, uom, qty, total
//        TextField carPriceTF = new TextField("Price");
//        TextField carUomTF = new TextField("UOM");
//        TextField carQuantityTF = new TextField("Quantity");
//        TextField carTotalTF = new TextField("Total"); //todo do the maths
//
//        HorizontalLayout amountsCarHL = new HorizontalLayout(carPriceTF, carUomTF, carQuantityTF, carTotalTF);
//
//        // Default discount + possibility to change it to any other or custom discount (via dropdown)
//        ComboBox<PricingGroup> discountsCarCB = new ComboBox<>("Discount (%)");
//        //todo set items
//        TextField discountAmountCarTF = new TextField("Discount amount"); // enabled if someone wants to correct the % amount
//        //todo set the value of discount
//
//        HorizontalLayout carDiscountsHL = new HorizontalLayout(discountsCarCB, discountAmountCarTF);
//
//        // The notes grid
//        //todo see the note at buildPackageItemNoteGrid
//
//        // Put it together
//        carItemVL.addComponents(locationPricingHL, dateTimeCarDescriptionHL, amountsCarHL, carDiscountsHL);
//
//        setMargin(false);
//        return carItemVL;
//
//        // todo at the save() stage check again if the car is still available
//
//    }
//
//    // Building the notes grid by package item id
//    //todo - here or in each of the three methods
//    public Grid<BookingPackageItemNote> buildPackageItemNoteGrid() {
//        Grid<BookingPackageItemNote> packageItemNoteGrid = new Grid<>(BookingPackageItemNote.class);
//
//        // The Add button
//
//        // if the item count equals 0, set grid visibility to false
//
//        return packageItemNoteGrid;
//    }
//
//    // Form open processing (new / edit)
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        String packageItemId = event.getParameters();
//        if ("".equals(packageItemId)) {
//            enterView(null);
//        } else {
//            enterView(Long.valueOf(packageItemId));
//        }
//    }
//
//    // Called when user enters view from the list or adding a new document
//    public void enterView(Long id) {
//        BookingPackageItem bookingPackageItem;
//        if (id == null) {
//            // New
//            bookingPackageItem = new BookingPackageItem();
//
//            bookingPackageItem.setUnderEditing(true);
//            // todo more setters
//        } else {
//            bookingPackageItem = bookingPackageItemRepository.findById(id).get();
//            if (bookingPackageItem.isUnderEditing()) {
//                Notification.show("Someone is editing this one now. Come back later.");
//            } else {
//                bookingPackageItem.setUnderEditing(true);
//                if (bookingPackageItem == null) {
//                    showNotFound();
//                    return;
//                }
//            }
//        }
//        itemBinder.setBean(bookingPackageItem);
//        // todo xx.focus();
//    }
//
//    // Won't hopefully happen
//    public void showNotFound() {
//        removeAllComponents();
//        addComponent(new Label("Item not found"));
//    }
//
//}
