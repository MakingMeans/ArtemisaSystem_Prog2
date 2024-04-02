package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.ProductFruit;
import co.edu.unbosque.model.ProductFruitDTO;
import co.edu.unbosque.model.persistence.ProductFruitDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ProductFruitService implements ServiceOperation<ProductFruitDTO> {

	private List<ProductFruitDTO> products;
	private ProductFruitDAO pdao = new ProductFruitDAO();

	@PostConstruct
	public void init() {
		pdao = new ProductFruitDAO();
		products=new ArrayList<>();
		products = readAll();
	}

	// mappeando de entity a dto
	public ProductFruitDTO toDto(ProductFruit ProductFruit) {
		ProductFruitDTO dto = new ProductFruitDTO();
		dto.setId(ProductFruit.getId());
		dto.setName(ProductFruit.getName());
		dto.setOriginCompany(ProductFruit.getOriginCompany());
		dto.setFabricCost(ProductFruit.getFabricCost());
		dto.setSellCost(ProductFruit.getSellCost());
		dto.setWholesalePrice(ProductFruit.getWholesalePrice());
		dto.setHasAcid(ProductFruit.isHasAcid());
		return dto;
	}

	// mappeando de entity a dto
	public ProductFruit toEntity(ProductFruitDTO ProductFruitDto) {
		ProductFruit entity = new ProductFruit();
		entity.setId(ProductFruitDto.getId());
		entity.setName(ProductFruitDto.getName());
		entity.setOriginCompany(ProductFruitDto.getOriginCompany());
		entity.setFabricCost(ProductFruitDto.getFabricCost());
		entity.setSellCost(ProductFruitDto.getSellCost());
		entity.setWholesalePrice(ProductFruitDto.getWholesalePrice());
		entity.setHasAcid(ProductFruitDto.isHasAcid());
		return entity;
	}

	

	public List<ProductFruitDTO> getProducts(int size) {

		if (size > products.size()) {
			List<ProductFruitDTO> shortenedList = new ArrayList<>();
			for (int i = 0; i < products.size() - 1; i++) {
				shortenedList.add(products.get(i));
			}
			return shortenedList;
		}

		else {
			return new ArrayList<>(products.subList(0, size));
		}

	}

	@Override
	public void create(ProductFruitDTO obj) {
		pdao.create(toEntity(obj));
		products=readAll();

	}

	@Override
	public boolean delete(long id) {
		boolean result = pdao.delete(id);
		products=readAll();
		return result;
	}

	@Override
	public boolean update(long id, ProductFruitDTO obj) {
		boolean result = pdao.update(id, toEntity(obj));
		products=readAll();
		return result;
	}

	@Override
	public List<ProductFruitDTO> readAll() {
		products.clear();
		ArrayList<ProductFruit> entities = pdao.readAll();
		for (ProductFruit ProductFruit : entities) {
			products.add(toDto(ProductFruit));
		}
		return products;
	}

	@Override
	public ProductFruitDTO findOne(long id) {
		ProductFruitDTO find = toDto(pdao.findOne(id));
		return find;
	}

	public List<ProductFruitDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductFruitDTO> products) {
		this.products = products;
	}

	public ProductFruitDAO getPdao() {
		return pdao;
	}

	public void setPdao(ProductFruitDAO pdao) {
		this.pdao = pdao;
	}
	
	

}