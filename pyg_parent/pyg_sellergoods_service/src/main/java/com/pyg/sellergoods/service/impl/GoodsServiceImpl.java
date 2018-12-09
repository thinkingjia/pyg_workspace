package com.pyg.sellergoods.service.impl;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.*;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbItem;
import com.sun.tools.javac.api.ClientCodeWrapper;
import groupEntity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsExample;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.sellergoods.service.GoodsService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private TbBrandMapper brandMapper;
	@Autowired
	private TbSellerMapper sellerMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		TbGoods tbGoods = goods.getTbGoods();
		tbGoods.setAuditStatus("0");
		tbGoods.setIsMarketable("0");
		tbGoods.setIsDelete("0");
		goodsMapper.insert(tbGoods);

		TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
		tbGoodsDesc.setGoodsId(tbGoods.getId());
		goodsDescMapper.insert(tbGoodsDesc);

		List<TbItem> itemList = goods.getItemList();
		for (TbItem tbItem : itemList) {
			String title = tbGoods.getGoodsName();
			String spec = tbItem.getSpec(); //{"网络":"移动4G","机身内存":"32G"}
			Map<String,String> specMap = JSON.parseObject(spec, Map.class);
			for(String key:specMap.keySet()){
				title+=" "+specMap.get(key);
			}
			tbItem.setTitle(title);
			tbItem.setSellPoint(tbGoods.getCaption());
			String itemImages = tbGoodsDesc.getItemImages(); //[{color:,url:}]
			List<Map> itemImageMapList = JSON.parseArray(itemImages, Map.class);
			if(itemImageMapList.size()>0){
				String url = (String) itemImageMapList.get(0).get("url");
				tbItem.setImage(url);
			}
			tbItem.setCategoryid(tbGoods.getCategory3Id());
			tbItem.setCreateTime(new Date());
			tbItem.setUpdateTime(new Date());
			tbItem.setGoodsId(tbGoods.getId());
			tbItem.setSellerId(tbGoods.getSellerId());
			tbItem.setCategory(itemCatMapper.selectByPrimaryKey(tbItem.getCategoryid()).getName());
			tbItem.setBrand(brandMapper.selectByPrimaryKey(tbGoods.getBrandId()).getName());
			tbItem.setSeller(sellerMapper.selectByPrimaryKey(tbItem.getSellerId()).getName());
			itemMapper.insert(tbItem);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods findOne(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();

		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateAuditStatus(Long[] ids, String auditStatus) {
//		update tb_goods set audit_status=? where id=?
		for (Long id : ids) {
//			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
//			tbGoods.setAuditStatus(auditStatus);
//			goodsMapper.updateByPrimaryKey(tbGoods);
			Map paramMap = new HashMap();
			paramMap.put("id",id);
			paramMap.put("auditStatus",auditStatus);
			goodsMapper.updateAuditStatus(paramMap);
		}
	}

	@Override
	public void updateIsMarketable(Long[] ids, String market) {
		//		update tb_goods set is_marketable=? where id=?
		for (Long id : ids) {
//			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
//			tbGoods.setIsMarketable(market);
//			goodsMapper.updateByPrimaryKey(tbGoods);
			Map paramMap = new HashMap();
			paramMap.put("id",id);
			paramMap.put("market",market);
			goodsMapper.updateIsMarketable(paramMap);
		}
	}

}
