package Base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BasePresenter<T> {
    private final CompositeSubscription compositeSubscription=new CompositeSubscription();
    protected void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
}
