package com.DAD.MercadoCentral.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.DAD.MercadoCentral.extras.Category;
import com.DAD.MercadoCentral.models.BarcodeModel;
import com.DAD.MercadoCentral.models.ClientModel;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.models.WorkerModel;
import com.DAD.MercadoCentral.service.ClientService;
import com.DAD.MercadoCentral.service.ProductService;
import com.DAD.MercadoCentral.service.WorkerService;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private WorkerService ws;
	@Autowired
	private ProductService ps;

	@GetMapping("/")
	public String mainPage(Model m, HttpServletRequest request) {

		m.addAttribute("worker", true);

		m.addAttribute("category", Category.categoryList());

		if (request.isUserInRole("ROLE_" + "worker")) {

			return "main";

		}

		m.addAttribute("workers", ws.getAllWorkers());
		return "workersList";

	}

	@GetMapping("/category/{id}")
	public String productsList(Model m, @PathVariable long id) {

		m.addAttribute("worker", true);

		m.addAttribute("category", Category.categoryList());

		m.addAttribute("products", ps.getCategoryProducts(id));

		return "productsList";

	}

	@GetMapping("/product/{id}")
	public String productData(Model m, @PathVariable long id) {

		m.addAttribute("worker", true);

		ProductModel p = ps.getProdById(id);

		m.addAttribute("name", p.getName());

		m.addAttribute("product", p);

		m.addAttribute("quant", p.getQuant());

		if (p.getBarcode() >= 0)
			m.addAttribute("barcode", p.getBarcode());

		return "productDataMod";

	}

	@PostMapping("/modproduct/{id}")
	public String modProductData(Model m, @PathVariable long id, @RequestParam String name,
			int price, String photo, int stock) {

		ps.modifiyProduct(id, name, price, stock);

		return "redirect:/worker/";

	}

	@PostMapping("/modproduct/{id}/upload_image")
	public String changeImage(@PathVariable long id, @RequestParam MultipartFile image) throws IOException {

		ProductModel pm = ps.getProdById(id);

		pm.setImage(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
		ps.addProduct(pm);

		return "redirect:/worker/";

	}

	@GetMapping("/selectbarcode/{id}")
	public String selectBarcode(Model m, @PathVariable long id) {

		m.addAttribute("worker", true);

		m.addAttribute("asign", true);

		m.addAttribute("id", id);

		m.addAttribute("barcodes", ps.getAllBarcodeUnasigned());

		return "barcodesList";

	}

	@GetMapping("/asignbarcode/{id}/{barcodeId}")
	public String selectBarcode(Model m, @PathVariable long id, @PathVariable long barcodeId) {

		ps.asignBarcode(id, barcodeId);

		return "redirect:/worker/product/" + id;

	}

	@GetMapping("/adminbarcodes")
	public String selectBarcode(Model m) {

		m.addAttribute("worker", true);

		m.addAttribute("barcodes", ps.getAllBarcodes());

		return "barcodesList";

	}

	@PostMapping("/addbarcode")
	public String modProductData(Model m, @RequestParam long barcode) {

		ps.addBarcode(barcode);

		return "redirect:/worker/adminbarcodes";

	}

	@GetMapping("/deletebarcode/{barcodeId}")
	public String deleteBarcode(Model m, @PathVariable long barcodeId) {

		ps.deleteBarcode(barcodeId);

		return "redirect:/worker/adminbarcodes";

	}

	@GetMapping("/asignedproduct/{barcodeId}")
	public String asignedBarcode(Model m, @PathVariable long barcodeId) {

		m.addAttribute("worker", true);

		BarcodeModel b = ps.getBarcodeById(barcodeId);

		if (b.getProduct() == null)
			return "redirect:/worker/adminbarcodes";

		ProductModel p = ps.getProdById(b.getProduct().getId());

		m.addAttribute("name", p.getName());
		m.addAttribute("photo", p.getImage());
		m.addAttribute("price", p.getPrice());
		if (p.getBarcode() >= 0)
			m.addAttribute("barcode", p.getBarcode());
		m.addAttribute("quant", p.getQuant());

		m.addAttribute("quant", p.getQuant());

		return "productData";

	}

	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct(Model m, @PathVariable long id) {

		ps.deleteProductById(id);

		return "redirect:/worker/category/" + id / 1000000;

	}

	@GetMapping("/addproduct")
	public String addProduct(Model m) {

		m.addAttribute("worker", true);

		ProductModel p = new ProductModel(0, "", 0);

		m.addAttribute("new", true);

		m.addAttribute("name", "");

		m.addAttribute("product", p);

		m.addAttribute("quant", 0);

		return "productDataMod";

	}

	@PostMapping("/newproduct")
	public String addProductData(Model m, @RequestParam long id,
			String name,
			int price,
			int stock) {

		// Modificar los datos del cliente

		ps.addProduct(id, name, price, stock);

		return "redirect:/worker/";

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/{id}")
	public String addWorker(Model m, @PathVariable long id) {

		m.addAttribute("worker", true);

		WorkerModel w = ws.getWorkerById(id);

		m.addAttribute("worker", w);

		return "modWorker";

	}

	@PostMapping("/modworker/{id}")
	public String modWorkerData(Model m, @PathVariable long id,
			@RequestParam String nickname,
			String nombre,
			Boolean admin,
			String password) {

		// Modificar los datos del cliente

		ws.modifiyWorker(id, nickname, nombre, admin, password);

		return "redirect:/worker/";

	}

	@GetMapping("/addworker")
	public String addWorker(Model m) {

		m.addAttribute("new", true);

		m.addAttribute("worker", new WorkerModel("", "", "", false));
		return "modWorker";

	}

	@PostMapping("/newworker")
	public String modWorkerData(Model m, @RequestParam String nickname,
			String nombre,
			Boolean admin,
			String password) {

		// Modificar los datos del cliente

		ws.addWorker(nickname, nombre, admin, password);

		return "redirect:/worker/";

	}

	@GetMapping("/deleteworker/{id}")
	public String deleteWorker(Model m, @PathVariable long id) {

		ws.deleteWorkerById(id);

		return "redirect:/worker/";

	}

}