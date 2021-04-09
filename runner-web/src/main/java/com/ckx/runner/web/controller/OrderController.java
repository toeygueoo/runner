package com.ckx.runner.web.controller;

import com.ckx.runner.core.domain.Customer;
import com.ckx.runner.core.domain.Order;
import com.ckx.runner.core.service.OrderService;
import com.ckx.runner.web.form.OrderForDeliverForm;
import com.ckx.runner.web.form.OrderForSellForm;
import com.ckx.runner.web.form.OrderForTakeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //进入下单页面
    @GetMapping("/index")
    public String index() {
        return "order/index";
    }

    /**
     * 进入帮我买下单页面
     *
     * @return
     */
    @GetMapping("/addForSell")
    public String addForSell() {
        return "order/addForSell";
    }

    /**
     * 执行帮我买下单操作，跳转到下单成功页面
     *
     * @return
     */
    @PostMapping("/addForSell")
    public String addForSell(@Validated OrderForSellForm orderForSellForm,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {

        if (bindingResult.hasErrors()){
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("errorMsg", errorMsg);
            return "order/addForSell";
        }

        //1.order数据来自表单
        Order order = new Order();
        order.setStore(orderForSellForm.getStore());
        order.setConsignee(orderForSellForm.getConsignee());
        order.setConsigneeMobile(orderForSellForm.getConsigneeMobile());
        order.setGoods(orderForSellForm.getGoods());
        order.setType(1);
        //2.下单客户ID，数据来源session
        Customer customer = (Customer) session.getAttribute("customer");

        //3.调用下单方法
        Order result = orderService.create(order, customer.getId());

        if (result != null){
            return "order/success";
        }else{
            return "publiz/error";
        }


    }

    /**
     * 进入帮我送下单页面
     *
     * @return
     */
    @GetMapping("/addForDeliver")
    public String addForDeliver() {
        return "order/addForDeliver";
    }

    /**
     * 执行帮我送下单操作，跳转到下单成功页面
     *
     * @return
     */
    @PostMapping("/addForDeliver")
    public String addForDeliver(@Validated OrderForDeliverForm orderForDeliverForm,
                                BindingResult bindingResult,
                                Model model,
                                HttpSession session) {

        if (bindingResult.hasErrors()){
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("errorMsg", errorMsg);
            return "order/addForDeliver";
        }

        //1.order数据来自表单
        Order order = new Order();

        order.setConsigner(orderForDeliverForm.getConsigner());
        order.setConsignerMobiel(orderForDeliverForm.getConsignerMobiel());
        order.setConsignee(orderForDeliverForm.getConsignee());
        order.setConsigneeMobile(orderForDeliverForm.getConsigneeMobile());
        order.setGoods(orderForDeliverForm.getGoods());
        order.setType(2);

        //2.下单客户ID，数据来源session
        Customer customer = (Customer) session.getAttribute("customer");

        //3.调用下单方法
        Order result = orderService.create(order, customer.getId());

        if (result != null){
            return "order/success";
        }else{
            return "publiz/error";
        }


    }


    /**
     * 进入帮我取下单页面
     *
     * @return
     */
    @GetMapping("/addForTake")
    public String addForTake() {
        return "order/addForTake";
    }

    /**
     * 执行帮我取下单操作，跳转到下单成功页面
     *
     * @return
     */
    @PostMapping("/addForTake")
    public String addForTake(@Validated OrderForTakeForm orderForTakeForm,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {

        if (bindingResult.hasErrors()){
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("errorMsg", errorMsg);
            return "order/addForSell";
        }

        //1.order数据来自表单
        Order order = new Order();
        order.setPickupAddress(orderForTakeForm.getPickupAddress());
        order.setConsignee(orderForTakeForm.getConsignee());
        order.setConsigneeMobile(orderForTakeForm.getConsigneeMobile());
        order.setGoods(orderForTakeForm.getGoods());
        order.setType(2);

        //2.下单客户ID，数据来源session
        Customer customer = (Customer) session.getAttribute("customer");

        //3.调用下单方法
        Order result = orderService.create(order, customer.getId());

        if (result != null){
            return "order/success";
        }else{
            return "publiz/error";
        }


    }

    private String getErrorMessage(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        int i = 0;
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (i != 0) {
                errorMsg.append(error.getDefaultMessage() + "<br/>");
            }
            errorMsg.append(error.getDefaultMessage());
            i++;
        }
        return errorMsg.toString();
    }
}