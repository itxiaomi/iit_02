package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.ContentCategoryMapper;
import com.itheima.pojo.ContentCategory;
import com.itheima.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ContentCategoryServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 9:05
 *  @描述：    TODO
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper mapper;

    @Override
    public List<ContentCategory> getCategoryByParentId(Long id) {


        ContentCategory category = new ContentCategory();

        category.setParentId(id);


        return mapper.select(category);

    }

    @Override
    public ContentCategory add(ContentCategory contentCategory) {

        //1. 直接添加这个分类到表  contentCategory ：  parentId , name
        contentCategory.setStatus(1);//正常使用
        contentCategory.setIsParent(false); //不是父亲分类
        contentCategory.setCreated(new Date());//创建时间
        contentCategory.setUpdated(new Date()); //更新时间

        mapper.insertSelective(contentCategory);

       /*
        Student stu = new Student();//id
        stu.setName("张三");
        stu.setAge(18);
        mapper.insert(stu);

        System.out.println(stu);*/


        //上面的代码针对的场景是：在父级分类下创建子分类。 如果我们是在子分类AA下创建子分类BB。
        //那么上面的代码仅仅只能添加 子分类BB， 并不会把子分类AA 变成父级分类。

        //2. 判断当前这个分类的父亲是不是子分类。 如果是子分类，那么要把这个父亲变成父级分类。
        Long parentId = contentCategory.getParentId();
        ContentCategory parentCategory = mapper.selectByPrimaryKey(parentId);

        //判定它的父亲是不是子级分类
        if(!parentCategory.getIsParent()){

            //让它的父亲是父级分类
            parentCategory.setIsParent(true);
        }

        mapper.updateByPrimaryKeySelective(parentCategory);

        //为什么在这里要返回一个对象。

        //1.  如果不返回的话，分类的条目无法绑定对象。 也就是添加好的分类，根本就没有数据绑定，
        //以后如果要对这个分类进行删除 &  更新， 服务器是不知道的。
        //2.  如果不返回数据，那么在页面上再进行其他的添加操作，那么光标会乱跑。

        //结论： 要返回对象，返回的对象还是当前操作的添加对象。

        //这里返回的对象没有ID值，所以后面更新的时候，就不知道更新的是哪一个分类了。

        //contentCategory.setId(138L);

        System.out.println("contentCate=" + contentCategory);

        return contentCategory;


    }



    /*

        问题： 更新陷入了僵局 , 因为没有主键。

        两种办法：

            1.  在添加的时候，返回的对象就是当前添加的对象，以便页面能够让分类条目和对象数据进行绑定。
                缺少：id值。(只能选这种。)  ---> 古老的办法： 自己去查 ， mybatis 添加返回id值

            2.  在添加完毕的时候，不要返回当前对象，而是返回父级对象， 在更新的时候，传递过来的是名称 &  父亲的id值。
                更新的时候，可以使用父级id &  名称  查询条目，以便更新，但是传递过来的名称是修改后的名称，数据库
                根本没有这个名称。
     */

    @Override
    public int update(ContentCategory contentCategory) { //参数只有id & name 剩下的都是默认值


        int rows = mapper.updateByPrimaryKeySelective(contentCategory);

        System.out.println("rows=" + rows);

        return rows;
    }

    @Override
    public int delete(ContentCategory contentCategory) {


        //1. 现在只删除子级分类
        //int result = mapper.deleteByPrimaryKey(contentCategory);


        /*
            2. 上面针对的是删除一条子级分类，现在要考虑的是直接删除父级分类。

           100 90  AAA  ---> 直接删除AAA  id : 100
           101  100  BBB
           102  101   CCC
           103  102     DDD
           104  101   EEE
         */

        //先定义出来集合，以便一会用来装要删除的对象
        List<ContentCategory> list = new ArrayList<>();

        //得根据当前的删除的节点id ，找到它的所有孩子。
        //先往集合里面存当前本来应该删除的分类
        list.add(contentCategory);

        //还要去查询它的子级分类
        findAllChild(list , contentCategory.getId());


        //再去删除  到这里，list集合里面已经完全装好，要删除多少个分类了。
        int result =deleteAll(list);



        return result;
    }

    /**
     * 删除一个集合
     * @param list
     * @return
     */
    private int deleteAll(List<ContentCategory> list) {

        int result = 0 ;
        for (ContentCategory category : list) {
            result += mapper.delete(category);
        }
        return result;

    }

    /**
     * 查询给定的分类id的所有子分类，包含多重的子级分类
     * @param list  存储的集合
     * @param id  当前要查询的id
     */
    private void findAllChild(List<ContentCategory> list, Long id) {

        //找到当前的节点孩子。
        List<ContentCategory> childList = getCategoryByParentId(id);

        if(childList !=null && childList.size() > 0 ){
            //遍历这些子级分类
            for (ContentCategory category : childList) {


                //先往list集合里面添加这个分类
                list.add(category);

                //执行递归，再调用方法
                findAllChild(list , category.getId());
            }
        }
    }

}
