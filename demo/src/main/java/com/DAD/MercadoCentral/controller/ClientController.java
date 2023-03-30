package com.DAD.MercadoCentral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DAD.MercadoCentral.extras.Category;
import com.DAD.MercadoCentral.extras.ClientCart;
import com.DAD.MercadoCentral.models.ClientModel;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.service.ClientService;
import com.DAD.MercadoCentral.service.ProductService;


@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService cs;
	@Autowired
	private ProductService ps;
	@Autowired
	private ClientCart cart;
	
	@GetMapping("/")
	public String mainPage(Model m, HttpSession session) {
		
		m.addAttribute("category", Category.categoryList());
		return "mainClient";
				
	}
	
	@GetMapping("/category/{id}")
	public String productsList(Model m, HttpSession session, @PathVariable long id) {
				
		m.addAttribute("category", Category.categoryList());
		
		m.addAttribute("products", ps.getCategoryProducts(id));
		return "productsList"; 
		
	}
	
	@GetMapping("/product/{id}")
	public String productData(Model m, HttpSession session, @PathVariable long id) {
		
		ProductModel p = ps.getProdById(id);
				
		m.addAttribute("name", p.getName());
		m.addAttribute("photo", p.getImage());
		m.addAttribute("price", p.getPrice());
		if (p.getBarcode() >= 0)
			m.addAttribute("barcode", p.getBarcode());
		m.addAttribute("quant", p.getQuant());
		
		
		m.addAttribute("quant", p.getQuant());

		return "productData"; 
		
	}
	
	@PostMapping("/purchase/{id}")
	public String purchase(Model m, HttpSession session, @PathVariable long id,
														 @RequestParam int quantity) {
		
		//Añadir compra al carrito
		cart.addProduct(ps.purchase(id, quantity));
		
		return "redirect:/client/";
		
	}
	
	@GetMapping("/shopingcart")
	public String shopingCart(Model m, HttpSession session) {
		
		m.addAttribute("cart", cart.getCart());
		
		return "shopingcart";
		
	}
	
	@PostMapping("/modproductquant/{id}")
	public String modProductQuant(Model m, HttpSession session, @PathVariable long id, 
																@RequestParam int quant) {
		// Modificar la cantidad
		cart.modCartCant(id, quant);
		
		return "redirect:/client/shopingcart";
		
	}
	
	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct(Model m, HttpSession session, @PathVariable long id) {
		
		// Eliminar producto de la cesta de la compra
		cart.deleteProd(id);
		
		return "redirect:/client/shopingcart";
		
	}
	
	@GetMapping("/buy")
	public String payWall(Model m, HttpSession session) {
		
		// comprobar si hay unidades de todo, sino, modifica cantidades y volver al carrito de la compra
		if (cart.checkAviability()) {
			ClientModel c = cs.getById((long)session.getAttribute("clientId"));
		
			m.addAttribute("card", c.getCreditCard());
		
			return "payWall";
			
		}
		
		return "redirect:/client/buy";
		
	}
	
	@PostMapping("/payconfirmation")
	public String payConfirmation(Model m, HttpSession session, @RequestParam String cardNumber) {
		
		// Se podría hacer algo con el numero de la tarjeta de credito
		
		
		return "payConfirmation";
		
	}
	
	@GetMapping("/confirmedpurchase")
	public String endOfTransaction(Model m, HttpSession session) {
		
		// vaciar el carrito de la compra
		cart.voidCart();
		
		return "redirect:/client/";
		
	}
	
	@GetMapping("/modclient") 
	public String modClientData(Model m, HttpSession session) {
		
		// Si ya estoy logueado el boton sirve para modificar tus datos
		ClientModel c = cs.getById((long)session.getAttribute("clientId"));
		
		m.addAttribute("client", c);
		
		return "modClient";
				
	}
	
	@PostMapping("/modclient") 
	public String modClientData(Model m, HttpSession session, @RequestParam String nickname, 
																			String nombre, 
																			String email, 
																			String direccion, 
																			String pago, 
																			String password) {
		
		// Modificar los datos del cliente

		cs.modifiyClient((long)session.getAttribute("ClientId"), nickname, nombre, email,direccion, pago, password);

		return "redirect:/modclient";
		
		
	}
	
	
}
