package com.tts.ecommerce.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	
	private int quantity;	
	
	private double price;
	
	private String description;
	
	private String name;
	
	private String category;
	
	private String image;
	
	private String brand;
}
