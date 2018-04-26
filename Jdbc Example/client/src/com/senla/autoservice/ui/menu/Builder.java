package com.senla.autoservice.ui.menu;

import java.util.HashMap;
import java.util.Map;

import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.ui.action.ExitAction;
import com.senla.autoservice.ui.action.NoAction;
import com.senla.autoservice.ui.action.mastermenu.AddMasterAction;
import com.senla.autoservice.ui.action.mastermenu.ByAlphaAction;
import com.senla.autoservice.ui.action.mastermenu.ByBusyingAction;
import com.senla.autoservice.ui.action.mastermenu.ExportMasterAction;
import com.senla.autoservice.ui.action.mastermenu.ImportMasterAction;
import com.senla.autoservice.ui.action.mastermenu.ShowCloseFreeDateAction;
import com.senla.autoservice.ui.action.mastermenu.ShowMasterCarrOutOnMasterAction;
import com.senla.autoservice.ui.action.mastermenu.ShowMastersAction;
import com.senla.autoservice.ui.action.ordermenu.ByDateOfCompletionAction;
import com.senla.autoservice.ui.action.ordermenu.ByOrderDateAction;
import com.senla.autoservice.ui.action.ordermenu.ByPriceAction;
import com.senla.autoservice.ui.action.ordermenu.ByStartDateAction;
import com.senla.autoservice.ui.action.ordermenu.ChangeStatusAction;
import com.senla.autoservice.ui.action.ordermenu.CloneOrderAction;
import com.senla.autoservice.ui.action.ordermenu.CurrByDateOfCompletionAction;
import com.senla.autoservice.ui.action.ordermenu.CurrByOrderDateAction;
import com.senla.autoservice.ui.action.ordermenu.CurrByPriceAction;
import com.senla.autoservice.ui.action.ordermenu.ShowCurrentOrderAction;
import com.senla.autoservice.ui.action.ordermenu.ShowOrderCarriedOutOnMasterAction;
import com.senla.autoservice.ui.action.ordermenu.ShowOrderForPeroidTimeAction;
import com.senla.autoservice.ui.action.ordermenu.ShowOrdersAction;
import com.senla.autoservice.ui.action.ordermenu.StatusBronedAction;
import com.senla.autoservice.ui.action.ordermenu.StatusClosedAction;
import com.senla.autoservice.ui.action.ordermenu.StatusDeletedAction;
import com.senla.autoservice.ui.action.ordermenu.StatusOpenedAction;
import com.senla.autoservice.ui.action.placemenu.AddPlace;
import com.senla.autoservice.ui.action.placemenu.CountOfFreePlacesAction;
import com.senla.autoservice.ui.action.placemenu.ExportPlacesAction;
import com.senla.autoservice.ui.action.placemenu.ImportPlaceAction;
import com.senla.autoservice.ui.action.placemenu.ShowFreePlacesAction;

class Builder {
	private Menu rootMenu;

	Map<MenuType, Menu> buildMenu() {
		final Autoservice autoservice = Autoservice.getInstance();
		final Map<MenuType, Menu> response = new HashMap<>();

		rootMenu = new Menu("Root Menu");
		final Menu placeMenu = new Menu("Place Menu");

		final Menu orderMenu = new Menu("Order Menu");
		final Menu orderSortMenu = new Menu("Order sort Menu");
		final Menu curOrderSortMenu = new Menu("Current order sort Menu");
		final Menu orderStatusMenu = new Menu("Status sort Menu");

		final Menu masterMenu = new Menu("MasterMenu");
		final Menu masterSortMenu = new Menu("Master sort Menu");

		rootMenuInit(placeMenu, orderMenu, masterMenu);
		response.put(MenuType.Root, rootMenu);

		placeMenuInit(autoservice, placeMenu);
		response.put(MenuType.Place, placeMenu);

		masterMenuInit(autoservice, masterMenu, masterSortMenu);
		response.put(MenuType.Master, masterMenu);

		orderMenuInit(autoservice, orderMenu, orderSortMenu, curOrderSortMenu, orderStatusMenu);
		response.put(MenuType.Order, orderMenu);

		return response;
	}

	private void orderMenuInit(Autoservice autoservice, final Menu orderMenu, final Menu orderSortMenu,
			final Menu curOrderSortMenu, final Menu orderStatusMenu) {
		orderMenu.addItem(new MenuItem("Show orders", orderSortMenu, new ShowOrdersAction()));
		orderMenu.addItem(new MenuItem("Show current order", curOrderSortMenu, new ShowCurrentOrderAction()));
		orderMenu.addItem(
				new MenuItem("Show order carried out on master", rootMenu, new ShowOrderCarriedOutOnMasterAction()));
		orderMenu.addItem(new MenuItem("Show order for peroid time. Status:", orderStatusMenu,
				new ShowOrderForPeroidTimeAction()));
		orderMenu.addItem(new MenuItem("Clone order", rootMenu, new CloneOrderAction()));
		orderMenu.addItem(new MenuItem("Change Status", rootMenu, new ChangeStatusAction()));
		
		orderSortMenu.addItem(new MenuItem("By Price", rootMenu, new ByPriceAction()));
		orderSortMenu.addItem(new MenuItem("By Start Date", rootMenu, new ByStartDateAction()));
		orderSortMenu.addItem(new MenuItem("By Date of order", rootMenu, new ByOrderDateAction()));
		orderSortMenu.addItem(new MenuItem("By Date of completion", rootMenu, new ByDateOfCompletionAction()));

		curOrderSortMenu.addItem(new MenuItem("By Price", rootMenu, new CurrByPriceAction()));
		curOrderSortMenu.addItem(new MenuItem("By Date of order", rootMenu, new CurrByOrderDateAction()));
		curOrderSortMenu.addItem(new MenuItem("By Date of completion", rootMenu, new CurrByDateOfCompletionAction()));

		orderStatusMenu.addItem(new MenuItem("Opened", rootMenu, new StatusOpenedAction()));
		orderStatusMenu.addItem(new MenuItem("Broned", rootMenu, new StatusBronedAction()));
		orderStatusMenu.addItem(new MenuItem("Deleted", rootMenu, new StatusDeletedAction()));
		orderStatusMenu.addItem(new MenuItem("Closed", rootMenu, new StatusClosedAction()));
	}

	private void masterMenuInit(Autoservice autoservice, final Menu masterMenu, final Menu masterSortMenu) {
		masterMenu.addItem(new MenuItem("Show masters", masterSortMenu, new ShowMastersAction()));
		masterMenu.addItem(new MenuItem("Show master carried out on order", rootMenu, new ShowMasterCarrOutOnMasterAction()));
		masterMenu.addItem(new MenuItem("Add Master", rootMenu, new AddMasterAction()));
		masterMenu.addItem(new MenuItem("Show close free date", rootMenu, new ShowCloseFreeDateAction()));
		masterMenu.addItem(new MenuItem("Export Masters", rootMenu, new ExportMasterAction()));
		masterMenu.addItem(new MenuItem("Import Masters", rootMenu, new ImportMasterAction()));

		masterSortMenu.addItem(new MenuItem("By busying", rootMenu, new ByBusyingAction()));
		masterSortMenu.addItem(new MenuItem("By alpha", rootMenu, new ByAlphaAction()));
	}

	private void placeMenuInit(Autoservice autoservice, final Menu placeMenu) {
		placeMenu.addItem(new MenuItem("Show Free Places", rootMenu, new ShowFreePlacesAction()));
		placeMenu.addItem(new MenuItem("Show count of free places on date", rootMenu, new CountOfFreePlacesAction()));
		placeMenu.addItem(new MenuItem("Add Place", rootMenu, new AddPlace()));
		placeMenu.addItem(new MenuItem("Export Places", rootMenu, new ExportPlacesAction()));
		placeMenu.addItem(new MenuItem("Import Places", rootMenu, new ImportPlaceAction()));
	}

	private void rootMenuInit(final Menu placeMenu, final Menu orderMenu, final Menu masterMenu) {
		rootMenu.addItem(new MenuItem("Place Menu", placeMenu, new NoAction()));
		rootMenu.addItem(new MenuItem("Order Menu", orderMenu, new NoAction()));
		rootMenu.addItem(new MenuItem("Master Menu", masterMenu, new NoAction()));
		rootMenu.addItem(new MenuItem("Exit", rootMenu, new ExitAction()));
	}

}
