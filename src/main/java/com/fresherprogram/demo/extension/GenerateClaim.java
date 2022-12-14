package com.fresherprogram.demo.extension;

import java.io.Serializable;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GenerateClaim implements IdentifierGenerator{
	private String prefix = "NO";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

		String query = "SELECT c.ClaimNo FROM Claim c";
		Stream<String> ids = session.createQuery(query, String.class).stream();
		Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
		return prefix + (String.format("%06d", max + 1));

	}
}
