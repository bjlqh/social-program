package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get(); //findById 返回的是Optional<T>
    }

    /**
     * 保存
     *
     * @param label
     */
    public String saveLabel(Label label) {
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
        return label.getId();
    }

    /**
     * 更新
     *
     * @param label
     */
    public void updateLabel(Label label) {
        labelDao.save(label);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    public void deleteLabel(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 条件查询
     *
     * @param label
     * @return
     */
    public List<Label> findByCondition(Label label) {
        Specification<Label> spec = this.getLabelSpecification(label);
        return labelDao.findAll(spec);
    }

    /**
     * 分页条件查询
     *
     * @param label
     * @param page  当前页
     * @param size  每页显示条数
     * @return
     */
    public PageResult<Label> findPageByCondition(Label label, int page, int size) {
        Specification<Label> spec = this.getLabelSpecification(label);
        /**PageRequest实现了Pageable接口，使用构造方法 或 静态of方法构造分页的条件
         * 第一个参数：当前页从0开始
         * 第二个参数：每页查询条数
         */
        //Pageable pageable = new PageRequest(page-1,size); 过时了
        Pageable pageable = PageRequest.of(page - 1, size);
        /**分页查询，封装为springdataJpa 内部的pageBean
         * getContent()  获取结果
         * getTotalPages() 获取总页数
         * getTotalElements() 获取总记录数
         */
        Page<Label> labelPage = labelDao.findAll(spec, pageable);
        PageResult<Label> pageResult = new PageResult<>(labelPage.getTotalElements(), labelPage.getContent());
        return pageResult;
    }


    /**
     * =====================================================================
     * 对于JpaSpecificationExecutor，这个接口基本是围绕着Specification接口来定义的。
     * 我们可以简单的理解为Specification构造的就是查询条件。
     *
     * @param label
     * @return
     */
    private Specification<Label> getLabelSpecification(Label label) {
        return new Specification<Label>() {
            /**
             *
             * @param root root接口，代表查询的根对象，可以通过root获取实体中的属性
             * @param criteriaQuery 代表一个顶层查询对象，用来自定义查询
             * @param criteriaBuilder 用来构建查询，此对象里有很多条件方法
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> list = new ArrayList<>();
                //1.labelname
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    Predicate p1 = criteriaBuilder.like(root.get("labelname"), "%" + label.getLabelname() + "%");
                    list.add(p1);
                }
                if (!StringUtils.isEmpty(label.getState())) {
                    Predicate p2 = criteriaBuilder.equal(root.get("state"), label.getState());
                    list.add(p2);
                }
                if (!StringUtils.isEmpty(label.getRecommend())) {
                    Predicate p3 = criteriaBuilder.equal(root.get("recommend"), label.getRecommend());
                    list.add(p3);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }
}
