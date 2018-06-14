package com.gmail.gak.artem.view;

import com.gmail.gak.artem.ImageStreamResource;
import com.gmail.gak.artem.component.GridPanel;
import com.gmail.gak.artem.component.Toolbar;
import com.gmail.gak.artem.entity.ImageEntity;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

@SpringView(name = ImageUploaderView.VIEW_NAME)
public class ImageUploaderView extends GridPanel<ImageEntity> implements View, Upload.StartedListener, Upload.Receiver, Upload.SucceededListener, SelectionListener<ImageEntity> {
    public static final String VIEW_NAME = "";

    private Map<Long, ImageEntity> images = new HashMap<>();
    private Set<ImageEntity> selected = new HashSet<>();
    private ByteArrayOutputStream outputStream;
    private Upload uploadB;
    private Button removeBtn = new Button("Remove", event -> remove());
    private Set<String> mimeTypes = new HashSet<>();
    private String filename;

    @PostConstruct
    public void init() {
        removeBtn.setEnabled(false);
        removeBtn.setStyleName(ValoTheme.BUTTON_DANGER);

        uploadB = new Upload(null, this);
        uploadB.setButtonStyleName("upload-btn");
        uploadB.addSucceededListener(this);
        uploadB.addStartedListener(this);

        addToolbarComponent(uploadB, Toolbar.POSITION.LEFT);
        addToolbarComponent(removeBtn, Toolbar.POSITION.LEFT);

        initGridView();
        initMIMETypes();
    }

    private void initGridColumns() {
        getGrid().addColumn(ImageEntity::getId).setCaption("ID");

        getGrid().addComponentColumn(entity -> {
            Image image = new Image(null, entity.getResource());
            image.setHeight(50, Unit.PIXELS);

            CssLayout layout = new CssLayout(image);
            layout.setSizeFull();
            layout.setStyleName("grid-image-cell");
            return layout;
        }).setCaption("Image").setWidth(100);

        getGrid().addComponentColumn(entity -> {
            Link link = new Link(entity.getResource().getFilename(), entity.getResource());
            link.setTargetName("_blank");
            link.setIcon(VaadinIcons.EXTERNAL_LINK);
            return link;
        }).setCaption("Link");
    }

    private void initGridView() {
        initGridColumns();
        getGrid().setBodyRowHeight(70);
        getGrid().setSelectionMode(Grid.SelectionMode.MULTI);
        getGrid().addSelectionListener(this);
        getGrid().setHeightMode(HeightMode.UNDEFINED);
        getGrid().setVisible(false);
    }

    private void initMIMETypes() {
        mimeTypes.add("image/jpeg");
        mimeTypes.add("image/png");
        mimeTypes.add("image/gif");
    }

    private boolean isMIMETypeValid (String type) {
        return mimeTypes.contains(type);
    }

    private void refresh() {
        if(images.size() == 0) {
            getGrid().setVisible(false);
            return;
        }
        getGrid().setItems(new ArrayList<ImageEntity>(images.values()));
    }

    private void remove() {
        for(ImageEntity image: selected) {
            images.remove(image.getId());
        }
        setEnableRemoveButton(false);
        Notification.show("Success",
                "Images removed.\n",
                Notification.Type.HUMANIZED_MESSAGE).setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        refresh();
    }

    private void setEnableRemoveButton(boolean enable) {
        if(enable) {
            removeBtn.setEnabled(true);
            removeBtn.setCaption("Remove (" + selected.size() + ")");
        } else {
            removeBtn.setEnabled(false);
            removeBtn.setCaption("Remove");
        }
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        if(!isMIMETypeValid(event.getMIMEType())) {
            uploadB.interruptUpload();
            Notification.show("Error",
                    "Uploaded file is not a valid image. Only JPG, PNG and GIF files are allowed.\n",
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.filename = filename;
        return outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        StreamResource resource = new StreamResource(new ImageStreamResource(outputStream), filename);
        ImageEntity image = new ImageEntity(resource);
        images.put(image.getId(), image);
        refresh();
        getGrid().setVisible(true);

        Notification.show("Success",
                "Images uploaded.\n",
                Notification.Type.HUMANIZED_MESSAGE).setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
    }

    @Override
    public void selectionChange(SelectionEvent<ImageEntity> event) {
        selected = event.getAllSelectedItems();
        setEnableRemoveButton((selected.size() > 0));
    }
}
