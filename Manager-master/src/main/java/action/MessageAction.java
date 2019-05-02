package action;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.net.httpserver.Authenticator;
import dao.MessageDao;
import model.Message;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import sun.applet.Main;
import util.DbUtil;
import util.ResponseUtil;

import java.sql.Connection;
import java.util.List;

public class MessageAction extends ActionSupport {
    private Message message;
    private String userName;
    private String mainPage;
    private List<Message> messageList;
    private String answer;
    private int messageId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    MessageDao messageDao = new MessageDao();

    DbUtil dbutil = new DbUtil();

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String list() throws Exception {
        Connection con = dbutil.getCon();
        messageList = messageDao.getList(con, userName);
        mainPage = "user/messageBoard.jsp";
        dbutil.closeCon(con);
        if (userName == null) {
            mainPage = "message/messageBoard.jsp";
            return "admin";
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        Connection con = dbutil.getCon();
        if (message.getAnswer() == null) {
            message.setAnswer("尚未回复");
        }
        messageDao.sendMessage(con, message,userName);
        messageList = messageDao.getList(con, userName);
        mainPage = "user/messageBoard.jsp";
        dbutil.closeCon(con);
        return SUCCESS;
    }
    public String answer() throws Exception {
        Connection connection = dbutil.getCon();
        Message m = new Message();
        m.setAnswer(answer);
        m.setId(messageId);
        messageDao.answer(connection, m);
        mainPage = "message/messageBoard.jsp";
        JSONObject result = new JSONObject();
        ResponseUtil.write(result, ServletActionContext.getResponse());
        return null;
    }
}
