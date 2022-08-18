package com.fresherprogram.demo.extension;

import com.fresherprogram.demo.model.Client;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.fresherprogram.demo.repository.ClientRepository;

public class Generate implements IdentifierGenerator {
	private String prefix = "CUS";
	private ClientRepository cRepo;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

		String query = "SELECT c.Id FROM Client c";
		Stream<String> ids = session.createQuery(query, String.class).stream();
		Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
		return prefix + (String.format("%05d", max + 1));

	}
	
	
}
