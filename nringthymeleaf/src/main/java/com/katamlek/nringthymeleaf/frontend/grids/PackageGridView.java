//package com.katamlek.nringthymeleaf.frontend.grids;
//
//import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
//import com.katamlek.nringthymeleaf.frontend.forms.BookingPackageItemForm;
//import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
//import com.katamlek.nringthymeleaf.repositories.BookingPackageItemRepository;
//import com.vaadin.navigator.View;
//import com.vaadin.shared.ui.grid.ColumnResizeMode;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.*;
//import com.vaadin.ui.renderers.ButtonRenderer;
//import org.assertj.core.util.Lists;
//
//import java.math.BigDecimal;
//
///**
// * List of the purchased services (package)
// * on the booking form.
// */
//
//@SpringComponent
//@UIScope
//public class PackageGridView extends VerticalLayout implements View {
//    private BookingPackageItemRepository bookingPackageItemRepository;
//    private NavigationManager navigationManager;
//    private Grid<BookingPackageItem> packageItemGrid;
//
//    public PackageGridView(BookingPackageItemRepository bookingPackageItemRepository, NavigationManager navigationManager) {
//        this.bookingPackageItemRepository = bookingPackageItemRepository;
//        this.navigationManager = navigationManager;
//        addComponent(buildPackageGrid());
//    }
//
//    // Build the grid with package items
//    public Grid<BookingPackageItem> buildPackageItemGrid() {
//        // Setting items - only items for the given booking id
//        packageItemGrid = new Grid<>(BookingPackageItem.class);
//        //todo how to pass the booking id parameter?
//        packageItemGrid.setItems(Lists.newArrayList(bookingPackageItemRepository.findAll())); //todo select conditions
//
//        // Setting visible colums according to specs
//        packageItemGrid.getColumn("id").setHidden(true);
//        packageItemGrid.getColumn("booking").setHidden(true);
//
//        packageItemGrid.addColumn(packageItem -> {
//            BigDecimal itemTotal = (packageItem.getQuantity().multiply(packageItem.getQuantity()));
//            return itemTotal;
//        }).setCaption("Total").setId("amountTotal");
//
//        // Setting column order
//        packageItemGrid.setColumnOrder("date", "startTime", "description", "unitPrice", "quantity", "amountTotal", "cancelled");
//
//        // Set columns hideable, reordering etc.
//        packageItemGrid.getColumns().forEach(column -> column.setSortable(true));
//        packageItemGrid.setColumnReorderingAllowed(true);
//        packageItemGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // Set open details for editing on double click
//        packageItemGrid.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                UI.getCurrent().getNavigator().navigateTo("xxx");
//                // todo set navigator to the selected car
//                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
//            }
//        });
//
//        // Inline filtering - no filtering in that case
////        this.filter = new GridCellFilter(packageItemGrid);
////        this.filter.setTextFilter("model", true, true);
////        this.filter.setTextFilter("plate", true, true);
////
////        this.filter.setNumberFilter("kmIn", BigDecimal.class);
////        this.filter.setNumberFilter("kmOut", BigDecimal.class);
////
////        CellFilterComponent<ComboBox<CarStatus>> statusFilter = this.filter.setComboBoxFilter("carStatus", CarStatus.class, Arrays.asList(CarStatus.values()));
////        this.filter.setBooleanFilter("currentlyInUse");
////
////        this.filter.setNumberFilter("kmTotal", BigDecimal.class);
//
//        // Inline editor - only for the cancelled field
//        //todo https://stackoverflow.com/questions/30887082/how-to-make-only-some-columns-editable-in-a-vaadin-grid
//        //todo if the bellow works leave as is
//        packageItemGrid.getColumn("cancelled").setEditable(true);
//
//        // Extra columns: edit, delete
//        // Commenting out - you can only change cancelled value via inline editor
////        packageItemGrid.addColumn(bookingCar -> "Edit", new ButtonRenderer(clickEvent -> {
////            //todo navigator
////        }));
//// --->>> Commenting out - delete disabled, can only cancel item
////
////        packageItemGrid.addColumn(bookingCar -> "Delete", new ButtonRenderer(clickEvent -> {
////            bookingPackageItemRepository.delete((BookingPackageItem) clickEvent.getItem());
////        }));
////
////        packageItemGrid.setSizeFull();
//
//        packageItemGrid.setSizeFull();
//
//        return packageItemGrid;
//    }
//
//    // Commenting out - no HL necessary, will just place an Add button above the grid
//    // Build the buttons' row: Add, Clear filters
//    public HorizontalLayout buildBookingCarButtons() {
//        HorizontalLayout buttonsBookingCarHL = new HorizontalLayout();
//
//        Button addBookingCarBtn = new Button("Add a car"); // add new car
//        addBookingCarBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
//        //todo add navigator
//
//        // Commenting out - not required here as there are no filtering options
////        Button clearAllFilters = new Button("Remove filters", e -> {
////            filter.clearAllFilters();
////        });
//
//        buttonsBookingCarHL.addComponents(addBookingCarBtn);
//        return buttonsBookingCarHL;
//    }
//
//    // Put all together
//    public VerticalLayout buildPackageGrid() {
//        VerticalLayout bookingCarGridViewVL = new VerticalLayout();
//
//        Button addPackageItemBtn = new Button("Add item");
//        addPackageItemBtn.addClickListener(e -> {
//           navigationManager.navigateTo(BookingPackageItemForm.class);
//        });
//
//        bookingCarGridViewVL.addComponents(addPackageItemBtn, buildPackageItemGrid());
//        return bookingCarGridViewVL;
//    }
//}
