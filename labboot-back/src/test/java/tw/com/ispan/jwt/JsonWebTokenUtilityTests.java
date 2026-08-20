package tw.com.ispan.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonWebTokenUtilityTests {
	@Autowired
	private JsonWebTokenUtility jwtUtil;
	
	@Test
	public void testCr() {
		String data = "this is a demo data";
		
		String token = jwtUtil.createToken(data);
		System.out.println("token="+token);
		
		String subject = jwtUtil.validateToken(token);
		System.out.println("subject="+subject);	
	}
}
