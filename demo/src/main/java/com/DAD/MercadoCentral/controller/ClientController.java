package com.DAD.MercadoCentral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ClientController {

	@Autowired
	private ClientService cs;
	@Autowired
	private ProductService ps;
	@Autowired
	private ClientCart cart;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String mainPage(Model m, HttpServletRequest request) {

		m.addAttribute("worker", false);

		m.addAttribute("isLogged", request.getSession().getAttribute("isLogged"));

		m.addAttribute("category", Category.categoryList());
		return "main";

	}

	@GetMapping("/category/{id}")
	public String productsList(Model m, HttpServletRequest request, @PathVariable long id) {

		m.addAttribute("isLogged", request.getSession().getAttribute("isLogged"));

		m.addAttribute("category", Category.categoryList());
		m.addAttribute("products", ps.getCategoryProducts(id));

		return "productsList";

	}

	@GetMapping("/product/{id}")
	public String productData(Model m, HttpServletRequest request, @PathVariable long id) {

		m.addAttribute("isLogged", request.getSession().getAttribute("isLogged"));

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
	public String purchase(Model m, @PathVariable long id, @RequestParam int quantity) {

		cart.addProduct(ps.purchase(id, quantity));

		return "redirect:/";

	}

	@GetMapping("/shopingcart")
	public String shopingCart(Model m, HttpServletRequest request) {

		m.addAttribute("worker", false);

		m.addAttribute("isLogged", request.getSession().getAttribute("isLogged"));

		m.addAttribute("cart", cart.getCart());

		return "shopingcart";

	}

	@PostMapping("/modproductquant/{id}")
	public String modProductQuant(Model m, @PathVariable long id,
			@RequestParam int quant) {

		cart.modCartCant(id, quant);

		return "redirect:/shopingcart";

	}

	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct(Model m, @PathVariable long id) {

		cart.deleteProd(id);

		return "redirect:/shopingcart";

	}

	@GetMapping("/buy")
	public String payWall(Model m, HttpServletRequest request) {

		if (cart.getCart().size() == 0)
			return "redirect:/";

		if (cart.checkAviability()) {
			ClientModel c = cs.getClientByNickname(request.getUserPrincipal().getName());

			m.addAttribute("card", c.getCreditCard());

			return "payWall";

		}

		return "redirect:/buy";

	}

	@PostMapping("/payconfirmation")
	public String payConfirmation(Model m, @RequestParam String cardNumber) {

		// Se podría hacer algo con el numero de la tarjeta de credito

		return "payConfirmation";

	}

	@GetMapping("/confirmedpurchase")
	public String endOfTransaction(Model m) {

		// vaciar el carrito de la compra
		cart.voidCart();

		return "redirect:/";

	}

	@GetMapping("/modclient")
	public String modClientData(Model m, HttpServletRequest request) {

		m.addAttribute("worker", false);

		m.addAttribute("isLogged", request.getSession().getAttribute("isLogged"));

		// Si ya estoy logueado el boton sirve para modificar tus datos
		ClientModel c = cs.getClientByNickname(request.getUserPrincipal().getName());

		m.addAttribute("client", c);

		return "modClient";

	}

	@PostMapping("/modclient")
	public String modClientData(Model m, HttpServletRequest request, @RequestParam String nickname, String nombre,
			String email, String direccion, String pago, String password) {

		// Modificar los datos del cliente

		long id = cs.getClientByNickname(request.getUserPrincipal().getName()).getId();

		if (password != null)
			cs.modifiyClient(id, nickname, nombre, email, direccion, pago, passwordEncoder.encode(password));
		else
			cs.modifiyClient(id, nickname, nombre, email, direccion, pago, null);

		return "redirect:/logout";

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Login controller
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/login")
	public String login(Model m, HttpServletRequest request) {

		return "login";

	}

	@GetMapping("/loginout")
	public String logout(Model m, HttpServletRequest request) {

		request.getSession().setAttribute("isLogged", false);

		return "redirect:/";

	}

	@GetMapping("/logged")
	public String loged(Model m, HttpServletRequest request) {

		if (request.isUserInRole("ROLE_" + "client")) {

			request.getSession().setAttribute("isLogged", true);

			return "redirect:/";

		} else {

			return "redirect:/worker/";

		}

	}

	@GetMapping("/signin")
	public String sigIn(Model m, HttpServletRequest request) {

		m.addAttribute("worker", false);

		m.addAttribute("new", true);

		m.addAttribute("client", new ClientModel("", "", "", ""));

		return "modClient";

	}

	@PostMapping("/newClient")
	public String newClient(Model m, @RequestParam String nickname,
			String nombre,
			String email,
			String direccion,
			String pago,
			String password) {

		// Introducir el nuevo cliente y logearlo
		cs.addClient(nickname, nombre, email, direccion, pago, password);

		return "redirect:/login";

	}

}
