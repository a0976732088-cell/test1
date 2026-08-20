package tw.com.ispan.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.CustomerBean;
import tw.com.ispan.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	private CustomerRepository customerRepository;
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	public CustomerBean login(String username, String password) {
		if(username != null && username.length() != 0 &&
					password != null && password.length() != 0) {
			CustomerBean bean = customerRepository.findById(username).orElse(null);
			if(bean != null) {
				byte[] temp = bean.getPassword();	//資料庫抓出
				byte[] pass = password.getBytes();	//使用者輸入
				if(Arrays.equals(temp, pass)) {
					return bean;
				}
			}
		}
		return null;
	}
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		 if(username != null && username.length() != 0 && oldPassword != null && oldPassword.length() != 0) {
			 CustomerBean login = this.login(username, oldPassword);
			 if(login!=null && newPassword != null && newPassword.length() != 0) {
				 login.setPassword(newPassword.getBytes());
				 customerRepository.save(login);
				 
				 return true;
			 }
		 }
		return false;
	}
}
