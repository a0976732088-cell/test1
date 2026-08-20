package tw.com.ispan.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.repository.ProductRepository;
import tw.com.ispan.utils.DatetimeConverter;

@Service
@Transactional
public class ProductService {
	private ProductRepository productRepository;
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productRepository.count(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public List<ProductBean> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productRepository.find(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductBean findById(Integer id) {
		try {
			return productRepository.findById(id).orElse(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean exists(Integer id) {
		try {
			return productRepository.existsById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ProductBean> select(ProductBean bean) {
		List<ProductBean> result = null;
		if(bean!=null && bean.getId()!=null && !bean.getId().equals(0)) {
			ProductBean temp = productRepository.findById(bean.getId()).orElse(null);
			if(temp!=null) {
				result = new ArrayList<ProductBean>();
				result.add(temp);
			}
		} else {
			result = productRepository.findAll();
		}
		return result;
	}
	
	public ProductBean insert(ProductBean bean) {
		if(bean!=null && bean.getId()!=null) {
			if(!productRepository.existsById(bean.getId())) {
				return productRepository.save(bean);
			}
		}
		return null;
	}
	public ProductBean create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			String name = obj.isNull("name") ? null : obj.getString("name");
			Double price = obj.isNull("price") ? null : obj.getDouble("price");
			String make = obj.isNull("make") ? null : obj.getString("make");
			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");
			
			if(!productRepository.existsById(id)) {
				ProductBean insert = new ProductBean();
				insert.setId(id);
				insert.setName(name);
				insert.setPrice(price);
				if(make!=null && make.length()!=0) {
					insert.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
				} else {
					insert.setMake(null);
				}
				insert.setExpire(expire);
				
				return productRepository.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductBean update(ProductBean bean) {
		if(bean!=null && bean.getId()!=null) {
			if(productRepository.existsById(bean.getId())) {
				return productRepository.save(bean);
			}
		}
		return null;
	}
	public ProductBean modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			String name = obj.isNull("name") ? null : obj.getString("name");
			Double price = obj.isNull("price") ? null : obj.getDouble("price");
			String make = obj.isNull("make") ? null : obj.getString("make");
			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");

			ProductBean update = productRepository.findById(id).orElse(null);
			update.setName(name);
			update.setPrice(price);
			if(make!=null && make.length()!=0) {
				update.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
			} else {
				update.setMake(null);
			}
			update.setExpire(expire);
			
			return this.productRepository.save(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean delete(ProductBean bean) {
		if(bean!=null && bean.getId()!=null) {
			if(productRepository.existsById(bean.getId())) {
				productRepository.deleteById(bean.getId());
				return true;
			}
		}
		return false;
	}
	public boolean remove(Integer id) {
		try {
			productRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return false;
	}
}
