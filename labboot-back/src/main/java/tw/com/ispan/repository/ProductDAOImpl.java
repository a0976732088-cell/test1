package tw.com.ispan.repository;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.utils.DatetimeConverter;

@Repository
public class ProductDAOImpl implements ProductDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ProductBean> find(JSONObject obj) {
//		select * from product where ... order by id + 分頁
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 3 : obj.getInt("rows");
		String order = obj.isNull("order") ? "id" : obj.getString("order");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		
		Integer id = obj.isNull("id") ? null : obj.getInt("id");
		String name = obj.isNull("name") ? null : obj.getString("name");
		Double minPrice = obj.isNull("minPrice") ? null : obj.getDouble("minPrice");
		Double maxPrice = obj.isNull("maxPrice") ? null : obj.getDouble("maxPrice");
		String minMake = obj.isNull("minMake") ? null : obj.getString("minMake");
		String maxMake = obj.isNull("maxMake") ? null : obj.getString("maxMake");
		Integer minExpire = obj.isNull("minExpire") ? null : obj.getInt("minExpire");
		Integer maxExpire = obj.isNull("maxExpire") ? null : obj.getInt("maxExpire");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductBean> criteriaQuery = criteriaBuilder.createQuery(ProductBean.class);
		
//		from product
		Root<ProductBean> table = criteriaQuery.from(ProductBean.class);
		
//		where ...
		List<Predicate> predicates = new ArrayList<>();
		
		if(id!=null) {
			Predicate predicate = criteriaBuilder.equal(table.get("id"), id);
			predicates.add(predicate);
		}
		if(name!=null && name.length()!=0) {
			Predicate predicate = criteriaBuilder.like(table.get("name"), "%"+name+"%");
			predicates.add(predicate);
		}
		if(minPrice!=null) {
			Predicate predicate = criteriaBuilder.greaterThan(table.get("price"), minPrice);
			predicates.add(predicate);
		}
		if(maxPrice!=null) {
			Predicate predicate = criteriaBuilder.lessThan(table.get("price"), maxPrice);
			predicates.add(predicate);
		}
		if(minMake!=null) {
			java.util.Date make = DatetimeConverter.parse(minMake, "yyyy-MM-dd");
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(table.get("make"), make);
			predicates.add(predicate);
		}
		if(maxMake!=null) {
			java.util.Date make = DatetimeConverter.parse(maxMake, "yyyy-MM-dd");
			Predicate predicate = criteriaBuilder.lessThanOrEqualTo(table.get("make"), make);
			predicates.add(predicate);
		}
		if(minExpire!=null) {
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(table.get("expire"), minExpire);
			predicates.add(predicate);
		}
		if(maxExpire!=null) {
			Predicate predicate = criteriaBuilder.lessThan(table.get("expire"), maxExpire);
			predicates.add(predicate);
		}
		
		if(!predicates.isEmpty()) {
			criteriaQuery = criteriaQuery.where(predicates);
		}
		
//		order by ???
		if(dir) {
			Order o = criteriaBuilder.desc(table.get(order));
			criteriaQuery = criteriaQuery.orderBy(o);
		} else {
			Order o = criteriaBuilder.asc(table.get(order));
			criteriaQuery = criteriaQuery.orderBy(o);
		}
		
		TypedQuery<ProductBean> typedQuery = entityManager.createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<ProductBean> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
	@Override
	public Long count(JSONObject obj) {
//		select count(*) from product where ...
		Integer id = obj.isNull("id") ? null : obj.getInt("id");
		String name = obj.isNull("name") ? null : obj.getString("name");
		Double minPrice = obj.isNull("minPrice") ? null : obj.getDouble("minPrice");
		Double maxPrice = obj.isNull("maxPrice") ? null : obj.getDouble("maxPrice");
		String minMake = obj.isNull("minMake") ? null : obj.getString("minMake");
		String maxMake = obj.isNull("maxMake") ? null : obj.getString("maxMake");
		Integer minExpire = obj.isNull("minExpire") ? null : obj.getInt("minExpire");
		Integer maxExpire = obj.isNull("maxExpire") ? null : obj.getInt("maxExpire");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

//		from product
		Root<ProductBean> table = criteriaQuery.from(ProductBean.class);

//		select count(*)
		Expression<Long> col = criteriaBuilder.count(table);
		criteriaQuery = criteriaQuery.select(col);
		
//		where ...
		List<Predicate> predicates = new ArrayList<>();
		
		if(id!=null) {
			Predicate predicate = criteriaBuilder.equal(table.get("id"), id);
			predicates.add(predicate);
		}
		if(name!=null && name.length()!=0) {
			Predicate predicate = criteriaBuilder.like(table.get("name"), "%"+name+"%");
			predicates.add(predicate);
		}
		if(minPrice!=null) {
			Predicate predicate = criteriaBuilder.greaterThan(table.get("price"), minPrice);
			predicates.add(predicate);
		}
		if(maxPrice!=null) {
			Predicate predicate = criteriaBuilder.lessThan(table.get("price"), maxPrice);
			predicates.add(predicate);
		}
		if(minMake!=null) {
			java.util.Date make = DatetimeConverter.parse(minMake, "yyyy-MM-dd");
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(table.get("make"), make);
			predicates.add(predicate);
		}
		if(maxMake!=null) {
			java.util.Date make = DatetimeConverter.parse(maxMake, "yyyy-MM-dd");
			Predicate predicate = criteriaBuilder.lessThanOrEqualTo(table.get("make"), make);
			predicates.add(predicate);
		}
		if(minExpire!=null) {
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(table.get("expire"), minExpire);
			predicates.add(predicate);
		}
		if(maxExpire!=null) {
			Predicate predicate = criteriaBuilder.lessThan(table.get("expire"), maxExpire);
			predicates.add(predicate);
		}
		
		if(!predicates.isEmpty()) {
			criteriaQuery = criteriaQuery.where(predicates);
		}
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
		Long result = typedQuery.getSingleResult();
		if(result!=null) {
			return result;
		} else {
			return 0L;
		}
	}
}