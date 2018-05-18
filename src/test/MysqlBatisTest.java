
import java.util.List;

import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.voucher.manage.mapper.UsersMapper;

public class MysqlBatisTest {
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis2.xml");
		
		DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory) applicationContext.getBean("sqlSessionFactory");

		UsersMapper userService =defaultSqlSessionFactory.openSession().getMapper(UsersMapper.class);
				
		List list=userService.getAllFullUser(1, 10,0, null, null, null);
		
		MyTestUtil.print(list);
		
	}
	
}
