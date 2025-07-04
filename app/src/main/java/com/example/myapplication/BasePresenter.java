package com.example.myapplication;

/**
 * Presenter 是Model和View的中间衔接层，一个标准的Presenter中应该至少包含一个Model和一个View。<br/>
 * 在MVP模式中，View不允许与Model交互，View仅负责展示数据。
 * View通过Presenter来获取Model（注意不是操作Model，例如在本例中是通过操作Repository来获取的Model），
 * Presenter 获取到Model后来更新View。<br/>
 * 数据流通是这样的:
 *      ->           ----Model作为参数-->                 Remote data source
 * View    Presenter                      Repository ->
 *      <-            <--Model作为结果---                 Local data source
 * <br/>
 * 需要注意：Presenter 一般不含有业务逻辑，仅仅负责将Model返回的数据转换为View显示的数据<br/>
 *
 */
public interface BasePresenter {
    void start();
}
