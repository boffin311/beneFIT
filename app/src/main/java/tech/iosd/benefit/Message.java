package tech.iosd.benefit;

import android.media.Image;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class Message implements IMessage, MessageContentType.Image, MessageContentType
{
    private String id;
    private String text;
    private Author author;
    private Date createdAt;
    private Image image;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getImageUrl()
    {
        return image == null ? null : image.getImageUrl();
    }

    public Message(String _id, String _text, Author _author, Date _createdAt)
    {
        id = _id;
        text = _text;
        author = _author;
        createdAt = _createdAt;
    }
    public Message(String _id, Image _image, Author _author, Date _createdAt)
    {
        id = _id;
        image = _image;
        author = _author;
        createdAt = _createdAt;
    }
}