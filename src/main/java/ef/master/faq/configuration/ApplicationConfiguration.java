package ef.master.faq.configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfiguration {

  @Bean
  public MapperFacade mapperFacade(){
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

    return mapperFactory.getMapperFacade();
  }
}
