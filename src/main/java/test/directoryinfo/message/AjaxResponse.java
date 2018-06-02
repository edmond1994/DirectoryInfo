package test.directoryinfo.message;

/**
 * Объект со статусом и содержимым, полученным в ajax-запросе
 */
public class AjaxResponse {
    private String ajaxStatus;
    private Object data;

    public AjaxResponse(){

    }

    public AjaxResponse(String ajaxStatus, Object data){
        this.ajaxStatus = ajaxStatus;
        this.data = data;
    }

    public String getAjaxStatus() {
        return ajaxStatus;
    }

    public void setAjaxStatus(String ajaxStatus) {
        this.ajaxStatus = ajaxStatus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
