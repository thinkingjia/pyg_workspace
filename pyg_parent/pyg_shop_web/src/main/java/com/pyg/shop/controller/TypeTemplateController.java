package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.sellergoods.service.TypeTemplateService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 * [
	{
	"id":27,"text":"网络",
	"options":[
	{"id":113,"optionName":"电信4G","orders":6,"specId":27},
	{"id":117,"optionName":"双卡","orders":10,"specId":27}
	]
	},
	{
	"id":32,"text":"机身内存",
	"options":[
	{"id":100,"optionName":"16G","orders":3,"specId":32}
	]
	}
	]
	 */
	@RequestMapping("/findSpecList/{id}")
	public List<Map> findSpecList(@PathVariable("id") Long id){
		return typeTemplateService.findSpecList(id);
	}
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){
		return typeTemplateService.findAll();
	}


	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return typeTemplateService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne/{id}")
	public TbTypeTemplate findOne(@PathVariable("id") Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/dele")
	public Result delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param typeTemplate
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int pageNum, int pageSize  ){
		return typeTemplateService.findPage(typeTemplate, pageNum, pageSize);
	}
	
}
