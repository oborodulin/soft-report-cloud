package com.oborodulin.softreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;

/**
 * Класс запуска Spring Boot приложение Soft Report.
 * <p>
 * Приложение использует аудит (отслеживание изменений в сущностных классах).
 * Для Spring Data JPA/Data Envers аудита необходимо:
 * <ul>
 * <li>добавить зависимость в pom.xml 
 * {@code <!-- Spring Data JPA -->
 * 	<dependency> 
 * 		<groupId>org.springframework.data</groupId>
 * 		<artifactId>spring-data-jpa</artifactId> 
 * 	</dependency>
 *	<dependency>
 *		<groupId>org.springframework.data</groupId>
 *		<artifactId>spring-data-envers</artifactId>
 *	</dependency>
 * }
 * <li>добавляем аннотацию {@code @EnableJpaAuditing}, включающую поддержку аудита, 
 * в класс {@link com.oborodulin.softreport.domain.config.PersistenceConfig PersistenceConfig}
 * <li>в этом же классе {@link com.oborodulin.softreport.domain.config.PersistenceConfig PersistenceConfig} 
 * создаём бин получения данных о пользователе, изменившем запись {@link com.oborodulin.softreport.domain.config.PersistenceConfig#auditorAware() auditorAware}
 * <li>к базовому классу поддержки аудита сущностей {@link com.oborodulin.softreport.domain.common.entity.AuditableEntity AuditableEntity} 
 * добавляем слушатель обратного вызова {@code @EntityListeners(AuditingEntityListener.class)}   
 * <li>в классы сущностей добавить аннотацию {@code }
 * <li>для получения информации о ревизиях:
 * <ul>
 * <li>добавляем аннотацию {@code @EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)} 
 * в корневой класс приложения {@link SoftReportApplication}  
 * <li>базовsq интерфейс репозиториев JPA {@link com.oborodulin.softreport.domain.common.repository.CommonRepository CommonRepository} 
 * должен расширять интерфейс {@code RevisionRepository<E, Long, Long>} 
 * </ul>
 * </ul>
 * Для Hibernate Envers аудита необходимо:
 * <ul>
 * <li>добавить зависимость в pom.xml 
 * {@code <!-- Hibernate Envers -->
 *	<dependency>
 *		<groupId>org.hibernate</groupId>
 *		<artifactId>hibernate-envers</artifactId>
 *	</dependency>
 * }
 * <li>добавляем аннотацию {@code @EnableJpaAuditing}, включающую поддержку аудита, 
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SoftReportApplication {

	public static void main(String[] args) {
		// SpringApplication springApplication = new
		// SpringApplication(SoftReportApplication.class);
		// springApplication.addListeners(new PropertiesLogger());
		// springApplication.run(args);
		SpringApplication.run(SoftReportApplication.class, args);
	}

}
