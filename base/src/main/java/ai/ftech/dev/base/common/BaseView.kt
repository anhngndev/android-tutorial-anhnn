package ai.ftech.dev.base.common

import android.view.View

interface BaseView<T : Config> {

    /**
     * Cấu hình activity/fragment/dialog
     */
    fun getConfig(): T

    /**
     * Gọi trước khi khởi tạo view
     */
    fun onPrepareInitView() {}

    /**
     * Gọi khi gán instance cho data binding
     */
    fun onInitBinding() {}

    /**
     * Gọi sau khi đã khởi tạo view
     */
    fun onInitView()

    /**
     * Gọi sau khi view được khởi tạo, quan sát dữ liệu
     */
    fun onObserverViewModel() {}

    /**
     * Xử lý toàn bộ sự kiện
     *
     * @param view trả về view được bắt sự kiện
     */
    fun onViewClick() {}
}
