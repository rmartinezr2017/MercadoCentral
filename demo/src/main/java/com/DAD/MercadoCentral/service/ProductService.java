package com.DAD.MercadoCentral.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAD.MercadoCentral.models.BarcodeModel;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.repository.BarcodeRepository;
import com.DAD.MercadoCentral.repository.ProductRepository;
import com.mysql.cj.jdbc.Blob;

@Service
public class ProductService {

	@Autowired
	ProductRepository pr;
	@Autowired
	BarcodeRepository br;

	public List<ProductModel> getCategoryProducts(long id) {

		List<ProductModel> list = pr.findByIdBetween(id * 1000000 - 1, (id + 1) * 1000000 - 1);

		if (list.isEmpty())
			return null;

		return list;

	}

	public ProductModel getProdById(long id) {
		return pr.findById(id).orElseThrow();
	}

	public ProductModel purchase(long id, int quantity) {

		ProductModel p = pr.findById(id).get();
		p.setQuant(p.getQuant() - quantity);
		pr.save(p);
		ProductModel aux = new ProductModel(p.getId(), p.getName(), p.getPrice());
		aux.setBarcode(br.findByBarcodeId(p.getBarcode()));
		aux.setImage(p.getImage());
		aux.setQuant(quantity);
		return aux;

	}

	public void modifiyProduct(long id, String name, int price, int stock) {

		ProductModel p = pr.findById(id).get();
		p.setName(name);
		p.setPrice(price);
		// p.setBarcode(br.findByBarcodeId(barcode).get());
		p.setQuant(stock);
		pr.save(p);

	}

	public void addProduct(long id, String name, int price, int stock) {

		ProductModel p = new ProductModel(id, name, price);
		// p.setBarcode(br.findByBarcodeId(barcode).get());
		p.setQuant(stock);
		pr.save(p);

	}

	public void deleteProductById(long id) {

		ProductModel p = pr.findById(id).get();

		if (p.getBarcode() != -1) { // No tiene un codigo de barras asignado

			BarcodeModel b = br.findByBarcodeId(p.getBarcode());

			b.setProduct(null);
			;
			br.save(b);

			p.setBarcode(null);
			pr.save(p);

		}

		pr.deleteById(id);

	}

	public List<BarcodeModel> getAllBarcodeUnasigned() {
		return br.findAllByProduct(null);
	}

	public void asignBarcode(long id, long barcodeId) {
		BarcodeModel b = br.findByBarcodeId(barcodeId);
		ProductModel p = pr.findById(id).get();
		b.setProduct(p);
		p.setBarcode(b);
		br.save(b);
		pr.save(p);
	}

	public Object getAllBarcodes() {
		return br.findAll();
	}

	public BarcodeModel getBarcodeById(long barcodeId) {
		return br.findByBarcodeId(barcodeId);
	}

	public void deleteBarcode(long barcodeId) {

		BarcodeModel b = br.findByBarcodeId(barcodeId);

		ProductModel p = b.getProduct();

		if (p != null) { // Tiene asignada un producto

			p.setBarcode(null);
			pr.save(p);

			b.setProduct(null);
			br.save(b);

		}

		br.deleteById(barcodeId);

	}

	public void addBarcode(long barcodeId) {
		br.save(new BarcodeModel(barcodeId));

	}

	public void addProduct(ProductModel pm) {
		pr.save(pm);
	}

}
