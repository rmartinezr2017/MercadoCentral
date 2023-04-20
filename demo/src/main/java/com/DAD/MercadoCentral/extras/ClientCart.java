package com.DAD.MercadoCentral.extras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.repository.ProductRepository;

@Component
@SessionScope
public class ClientCart {

	@Autowired
	ProductRepository pr;

	private List<ProductModel> cart = new ArrayList<ProductModel>();

	public void addProduct(ProductModel p) {

		Iterator<ProductModel> it = cart.iterator();
		while (it.hasNext()) {

			ProductModel aux = it.next();
			if (aux.getId() == p.getId()) {

				aux.setQuant(aux.getQuant() + p.getQuant());

				return;

			}

		}

		this.cart.add(p);

	}

	public List<ProductModel> getCart() {
		return cart;
	}

	public void modCartCant(long id, int quant) {
		// TODO Auto-generated method stub

		Iterator<ProductModel> it = cart.iterator();
		while (it.hasNext()) {

			ProductModel aux = it.next();
			if (aux.getId() == id) {

				int delta = quant - aux.getQuant();

				ProductModel aux2 = pr.findById(id).get();
				aux2.setQuant(aux2.getQuant() - delta);
				pr.save(aux2);
				aux.setQuant(aux.getQuant() + delta);

				break;

			}

		}

	}

	public void deleteProd(long id) {

		Iterator<ProductModel> it = cart.iterator();
		while (it.hasNext()) {

			ProductModel aux = it.next();
			if (aux.getId() == id) {

				ProductModel aux2 = pr.findById(id).get();
				aux2.setQuant(aux2.getQuant() + aux.getQuant());
				pr.save(aux2);
				it.remove();

				break;

			}

		}

	}

	public boolean checkAviability() {
		return true;
		// TODO Auto-generated method stub

	}

	public void voidCart() {
		cart = new ArrayList<ProductModel>();
	}

}
