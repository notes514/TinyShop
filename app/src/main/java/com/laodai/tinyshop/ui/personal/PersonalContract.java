package com.laodai.tinyshop.ui.personal;


import com.laodai.tinyshop.entity.ScenicSpot;
import com.laodai.tinyshop.mvp.BaseModel;
import com.laodai.tinyshop.mvp.BasePresenter;
import com.laodai.tinyshop.mvp.BaseView;
import com.laodai.tinyshop.mvp.MvpListener;

import java.util.List;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface PersonalContract {

    interface PersonalModel extends BaseModel {
    }

    interface PersonalView extends BaseView {
    }

    abstract class PersonalPresenter extends BasePresenter<PersonalModel, PersonalView> {
    }

}
