package com.haramasu.multi_databases.product.repo;

import com.haramasu.multi_databases.product.data.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: Ding, Shuo
 * @description:
 * @create: 2019-01-22 16:29
 **/
@Repository
public interface ProductRepository extends JpaRepository<ProductModel,Integer> {
}
