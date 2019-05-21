package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xn.bmpos.manager.bmposmanager.domain.dao.Help;
import com.xn.bmpos.manager.bmposmanager.domain.dao.HelpText;
import com.xn.bmpos.manager.bmposmanager.domain.model.HelpModel;
import com.xn.bmpos.manager.bmposmanager.service.HelpService;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author hjx
 */
@RestController()
@RequestMapping("api")
public class HelpCoinController {

    @Autowired
    private HelpService helpService;


    /**
     * 获取帮助页面树形图
     *
     * @return
     */
    @GetMapping("/help")
    public Resp help() {
        List list = getHelpTree();
        return Resp.success(list);
    }


    /**
     * 获取对应类容
     *
     * @return
     */
    @PostMapping("/help")
    public Resp helpTest(@RequestParam("id") int id) {
        //todo  pid == childrenID
        HelpText helpText = helpService.SelectTestByPid(id);
        return Resp.success(helpText.getText());
    }


    public List<HelpModel> getHelpTree() {
        List<Help> helps = helpService.findAll();
        List<HelpModel> helpModels = new ArrayList<HelpModel>();
        for (Help help : helps) {
            HelpModel helpModel = new HelpModel();
            helpModel.setId(help.getId());
            helpModel.setName(help.getName());
            helpModel.setPid(help.getPid());
            helpModel.setOrderId(help.getOrderId());
            helpModels.add(helpModel);
        }
        return tease(helpModels);
    }

    private List<HelpModel> tease(List<HelpModel> deptVos) {
        List<HelpModel> rootList = new ArrayList<HelpModel>();

        Multimap<Integer, HelpModel> container = ArrayListMultimap.create();

        for (HelpModel deptVo : deptVos) {
            if (deptVo.getPid() == 0) {
                rootList.add(deptVo);
            }
            container.put(deptVo.getPid(), deptVo);
        }
        Collections.sort(rootList, new Comparator<HelpModel>() {
            public int compare(HelpModel o1, HelpModel o2) {
                return 1;
            }
        });
        hierachy(rootList, container);
        return rootList;
    }

    private void hierachy(List<HelpModel> deptVos, Multimap container) {
        for (HelpModel deptVo : deptVos) {
            List<HelpModel> deptChilds = (List<HelpModel>) container.get(deptVo.getId());
            deptVo.setChildren(deptChilds);
            Collections.sort(deptChilds, new Comparator<HelpModel>() {
                public int compare(HelpModel o1, HelpModel o2) {
                    return 1;
                }
            });
            hierachy(deptChilds, container);
        }
    }

}
