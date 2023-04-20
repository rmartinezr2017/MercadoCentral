package com.DAD.MercadoCentral;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.jdbc.Blob;

import org.aspectj.util.FileUtil;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.type.descriptor.java.BlobTypeDescriptor.BlobMutabilityPlan;
import org.springframework.stereotype.Component;

import com.DAD.MercadoCentral.extras.Category;
import com.DAD.MercadoCentral.models.BarcodeModel;
import com.DAD.MercadoCentral.models.ClientModel;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.models.WorkerModel;
import com.DAD.MercadoCentral.repository.BarcodeRepository;
import com.DAD.MercadoCentral.repository.ClientRepository;
import com.DAD.MercadoCentral.repository.ProductRepository;
import com.DAD.MercadoCentral.repository.WorkerRepository;

@Component
public class DatabaseInitializer {

	@Autowired
	private BarcodeRepository br;
	@Autowired
	private ClientRepository cr;
	@Autowired
	private ProductRepository pr;
	@Autowired
	private WorkerRepository wr;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	void init() throws IOException {

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		for (int i = 0; i < 10; i++) {

			BarcodeModel bm = new BarcodeModel(i);
			br.save(bm);

		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		for (int i = 0; i < 10; i++) {

			ClientModel cm = new ClientModel("name" + i, "client" + i, passwordEncoder.encode("password"),
					"name" + i + "@gmail.com");
			cm.setDirection("c/calle " + i);
			cm.setCreditCard("100 020 003 000 00" + i);

			cr.save(cm);

		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		List<Category> categories = Category.categoryList();

		for (int cat = 0; cat < 11; cat++) {

			for (int i = 0; i < 5; i++) {

				ProductModel pm = new ProductModel(cat * 1000000 + i, categories.get(cat).getName() + i, i * 10 + 5);

				URL url;
				try {

					url = new URL("https://pbs.twimg.com/profile_images/1610995828893618177/njvMH6iz_400x400.jpg");

					try (InputStream is = url.openStream()) {
						pm.setImage(BlobProxy.generateProxy(is.readAllBytes()));
					} catch (IOException e) {
						e.printStackTrace();
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				pm.setQuant(10);

				pr.save(pm);

			}

		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		for (int i = 0; i < 4; i++) {

			WorkerModel wm = new WorkerModel("workerName" + i, "worker" + i, passwordEncoder.encode("password"), false);
			wr.save(wm);

		}

		WorkerModel wm = new WorkerModel("adminName", "admin", passwordEncoder.encode("password"), true);
		wr.save(wm);

	}

}