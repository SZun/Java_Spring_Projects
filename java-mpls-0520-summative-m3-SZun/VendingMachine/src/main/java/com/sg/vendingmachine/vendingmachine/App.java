/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine;

import com.sg.vendingmachine.vendingmachine.controllers.VendingMachineController;
import com.sg.vendingmachine.vendingmachine.daos.VendingMachineFileDAO;
import com.sg.vendingmachine.vendingmachine.services.VendingMachineService;
import com.sg.vendingmachine.vendingmachine.views.VendingMachineView;

/**
 *
 * @author samg.zun
 */
public class App {

    public static void main(String[] args) {
        VendingMachineView view = new VendingMachineView();
        VendingMachineFileDAO dao = new VendingMachineFileDAO("vending_machine.txt");
        VendingMachineService service = new VendingMachineService(dao);

        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();
    }
}