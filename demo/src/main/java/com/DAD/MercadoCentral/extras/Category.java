package com.DAD.MercadoCentral.extras;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.Lob;
import javax.persistence.criteria.Path;

import org.aspectj.util.FileUtil;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.UrlResource;

public class Category {
	
	private long id;
	
	private String name;
	
	//private String image;
	
	
	private Blob image;
	
	public Category (long id, String name) {
		
		this.setId(id);
		this.name = name;
		
		
		URL url;
		try {
			
			url = new URL("https://png.pngtree.com/element_origin_min_pic/16/08/20/1557b806da20852.jpg");
			
	        try (InputStream is = url.openStream()) {
	            image = BlobProxy.generateProxy(is.readAllBytes());
	        } catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();			
		}	
		
		/*
		//this.image = "fdfsdfsddsf";
		
		File f;
		
		try {
			
			//f = File.createTempFile("photo", ".jpg");
			
			//URL url = new URL("https://png.pngtree.com/element_origin_min_pic/16/08/20/1557b806da20852.jpg");
						
			//f = url.getFile().getBytes();
			
			UrlResource u = new UrlResource(new URL("https://png.pngtree.com/element_origin_min_pic/16/08/20/1557b806da20852.jpg"));
			
			image = u.getInputStream();
			
			//this.image = url.getFile().getBytes();
			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
		
		java.io.File output;
		try {
			
			output = java.io.File.createTempFile("foto", ".jpg");
			
			FileUtils.copyURLToFile(new URL("https://png.pngtree.com/element_origin_min_pic/16/08/20/1557b806da20852.jpg"), output);
			
			this.photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(output));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//*/			
		
	}

	public String getName() {
		return name;
	}

	public Blob getPhoto() {
		return image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(Blob image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static List<Category> categoryList() {
		
		List<Category> categories = new ArrayList<Category>();
		
		categories.add(new Category(0, "Postres"));
		categories.add(new Category(1, "Fruteria"));
		categories.add(new Category(2, "Menaje"));
		categories.add(new Category(3, "Precocinados"));
		categories.add(new Category(4, "Dulces"));
		categories.add(new Category(5, "Embutidos"));
		categories.add(new Category(6, "Fruteria"));
		categories.add(new Category(7, "Pescaderia"));
		categories.add(new Category(8, "Carniceria"));
		categories.add(new Category(9, "Lacteos"));
		categories.add(new Category(10, "Congelados"));
		
		return categories;
		
	}

}