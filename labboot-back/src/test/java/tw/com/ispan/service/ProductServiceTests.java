package tw.com.ispan.service;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tw.com.ispan.domain.ProductBean;

@SpringBootTest
public class ProductServiceTests {
	@Autowired
	private ProductService productService;
	
	@Test
	public void testFind() {
		JSONObject json = new JSONObject()
//				.put("id", 1)
//				.put("name", "a")
//				.put("minPrice", 10)
//				.put("maxPrice", 20)
//				.put("minMake", "2007-03-01")
//				.put("maxMake", "2007-04-30")
//				.put("minExpire", 100)
//				.put("maxExpire", 300)
				.put("start", 0)
				.put("rows", 3)
				.put("order", "id")
				.put("dir", false);
		
		Long count = productService.count(json.toString());
		System.out.println("count="+count);
		
		List<ProductBean> products = productService.find(json.toString());
		for(ProductBean product : products) {
			System.out.println("product="+product);
		}
	}
//	@Test
	public void testSelect() {
		List<ProductBean> selects = productService.select(null);
		System.out.println("selects="+selects);
	}
}
