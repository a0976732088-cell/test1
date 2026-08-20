package tw.com.ispan.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.DetailBean;
import tw.com.ispan.repository.DetailRepository;

@Service
@Transactional
public class DetailService {
	private DetailRepository detailRepository;
	public DetailService(DetailRepository detailRepository) {
		this.detailRepository = detailRepository;
	}

	public DetailBean findById(Integer id) {
		if(id!=null) {
			Optional<DetailBean> optional = detailRepository.findById(id);
			if(optional!=null && optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
}
