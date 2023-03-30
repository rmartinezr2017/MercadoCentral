package com.DAD.MercadoCentral.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DAD.MercadoCentral.extras.Category;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.service.ClientService;
import com.DAD.MercadoCentral.service.ProductService;
import com.DAD.MercadoCentral.service.WorkerService;


@Controller
public class UserController {
	
	@Autowired
	ClientService cs;
	@Autowired
	WorkerService ws;
	@Autowired
	ProductService ps;
	
	@GetMapping("/")
	public String mainPage(Model m, HttpSession session) {
	
		//m.addAttribute("category", categories);
		
		return "main";
				
	}
	
	///*
	@GetMapping("/login") 
	public String login(Model m, HttpSession session){
		
		// Si ya estoy logueado el boton sirve para modificar tus datos		
		
		if (false){//(Boolean)session.getAttribute("loged")) {
			
			return "redirect:/client/modclient";
			
		}
		
		return "login";
		
	}
	//*/
	
	@GetMapping("/loged")
	public String loged(Model m, HttpServletRequest request) {		
		
		System.out.println("DSDSADdas");
		
		///*
			System.out.println(request.getUserPrincipal().getName());
		//*/
		System.out.println(request.isUserInRole("ROLE_" + "client"));
		System.out.println(request.isUserInRole("client"));
		System.out.println(request.isUserInRole("CLIENT"));
		System.out.println(request.isUserInRole("ROLE_" + "CLIENT"));
		System.out.println(request.isUserInRole("Client"));
		
		if (request.isUserInRole("ROLE_" + "client")) {
			
			System.out.println("client");
			
			return "redirect:/client/";
			
		} else {
			
			System.out.println("worker");
			
			return "redirect:/worker/";
			
		}
				
	}
	
	@GetMapping("/signin")
	public String logingerror(Model m, HttpSession session, HttpServletRequest request) {
		
		return "signin"; // No es un usuario registrado
		
	}
	
	@PostMapping("/newClient")
	public String newClient(Model m, HttpSession session, 
							@RequestParam String nickname, 
										  String nombre, 
										  String email, 
										  String direccion, 
										  String pago, 
										  String password) {
		
		// Introducir el nuevo cliente y logearlo
		cs.addClient(nickname, nombre, email, direccion, pago, password);
		
		session.setAttribute("loged", true);
		session.setAttribute("clientId", cs.getClientByNickname(nickname).getId());
		
		return "redirect:/client/";
		
	}
	
	@GetMapping("/logout") 
	public String logout(Model m, HttpSession session){
		
		// Si ya estoy logueado el boton sirve para modificar tus datos		
		if ((Boolean)session.getAttribute("loged")) {
			
			session.setAttribute("isWorker", false);
			session.setAttribute("loged", false);
			
			return "redirect:/";

		}			
		
		return "login";
		
	}
	
	@GetMapping("/download_image/product/{id}")
	public ResponseEntity<Object> downloadImageProduct(Model model, @PathVariable long id) {		
		
		ProductModel product = ps.getProdById(id);
		
		if (product.getImage() != null) {
			
			System.out.println("jklljkljkljk");
			
			 Resource image;
			try {
				image = new InputStreamResource (product.getImage().getBinaryStream());
				return ResponseEntity.ok()
					 			  .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					 			  .contentLength(product.getImage().length())
					 			  .body(image);
			} catch (SQLException e) {
				System.out.println("ytytyttyyt");
				e.printStackTrace();
				return ResponseEntity.notFound().build();
			}

			 
		 } else {			 
			 //e.printStackTrace();
			 System.out.println("fsfsdfsd");
			 return ResponseEntity.notFound().build();			 
		 }
		
	}

	@GetMapping("/download_image/category/{id}")
	public ResponseEntity<Object> downloadImageCategory(Model model, @PathVariable long id) {
						
		try {
			Resource image = new InputStreamResource(Category.categoryList().get((int)id).getPhoto().getBinaryStream());		
			return ResponseEntity.ok()
							 	 .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
							 	 .contentLength(Category.categoryList().get((int)id).getPhoto().length())
							 	 .body(image);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
							 
	}
	
}
