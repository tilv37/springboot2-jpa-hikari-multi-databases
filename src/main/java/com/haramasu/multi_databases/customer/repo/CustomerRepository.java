package com.haramasu.multi_databases.customer.repo;

import com.haramasu.multi_databases.customer.data.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: Ding, Shuo
 * @description:
 * @create: 2019-01-22 16:29
 **/
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer > {
}