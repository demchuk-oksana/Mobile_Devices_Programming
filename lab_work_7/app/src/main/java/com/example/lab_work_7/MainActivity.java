package com.example.lab_work_7;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView image1, image2, image3;
    private List<ImageView> imageViews;
    private Map<ImageView, Float> originalScales;
    private List<ImageView> selectedImages;
    private ActionMode actionMode;
    private boolean isUkrainian = false;
    private String currentAnimalType = "dogs";
    private Map<String, int[]> animalImages;

    private ImageView lastContextImage; // Зберігаємо останнє зображення для контекстного меню

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setTitle("");

        toolbar.setNavigationIcon(R.drawable.ic_language);
        toolbar.setPadding(16, 0, 0, 0);
        toolbar.setNavigationOnClickListener(v -> toggleLanguage());

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        imageViews = new ArrayList<>();
        imageViews.add(image1);
        imageViews.add(image2);
        imageViews.add(image3);

        originalScales = new HashMap<>();
        selectedImages = new ArrayList<>();

        setupAnimalImages();
        loadAnimalImages(currentAnimalType);
        setupImageClickListeners();
    }

    private void setupAnimalImages() {
        animalImages = new HashMap<>();

        animalImages.put("cats", new int[]{
                R.drawable.cat1, R.drawable.cat2, R.drawable.cat3
        });
        animalImages.put("dogs", new int[]{
                R.drawable.dog1, R.drawable.dog2, R.drawable.dog3
        });
        animalImages.put("birds", new int[]{
                R.drawable.bird1, R.drawable.bird2, R.drawable.bird3
        });
        animalImages.put("rabbits", new int[]{
                R.drawable.rabbit1, R.drawable.rabbit2, R.drawable.rabbit3
        });
        animalImages.put("hamsters", new int[]{
                R.drawable.hamster1, R.drawable.hamster2, R.drawable.hamster3
        });
    }

    private void loadAnimalImages(String animalType) {
        if (animalImages.containsKey(animalType)) {
            int[] imageResources = animalImages.get(animalType);
            for (int i = 0; i < imageViews.size() && i < imageResources.length; i++) {
                imageViews.get(i).setImageResource(imageResources[i]);
                imageViews.get(i).setVisibility(View.VISIBLE);
                float scale = 1.0f;
                originalScales.put(imageViews.get(i), scale);
                imageViews.get(i).setScaleX(scale);
                imageViews.get(i).setScaleY(scale);
            }
            currentAnimalType = animalType;
        }
    }

    private void setupImageClickListeners() {
        for (ImageView imageView : imageViews) {
            imageView.setOnClickListener(v -> {
                if (actionMode == null) {
                    actionMode = startActionMode(actionModeCallback);
                }
                toggleImageSelection((ImageView) v);
            });

            imageView.setOnLongClickListener(v -> {
                lastContextImage = (ImageView) v;
                openContextMenu(v);
                return true;
            });

            registerForContextMenu(imageView);
        }
    }

    private void toggleImageSelection(ImageView imageView) {
        if (selectedImages.contains(imageView)) {
            selectedImages.remove(imageView);
            imageView.setAlpha(1.0f);
        } else {
            selectedImages.add(imageView);
            imageView.setAlpha(0.5f);
        }

        if (selectedImages.isEmpty() && actionMode != null) {
            actionMode.finish();
        }

        if (actionMode != null) {
            actionMode.setTitle(selectedImages.size() + " selected");
        }
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(getString(R.string.selected));
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.cab_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.cab_delete) {
                for (ImageView image : selectedImages) {
                    image.setVisibility(View.INVISIBLE);
                }
                mode.finish();
                return true;
            } else if (id == R.id.cab_increase_size) {
                for (ImageView image : selectedImages) {
                    float scale = originalScales.get(image);
                    scale += 0.2f;
                    originalScales.put(image, scale);
                    image.setScaleX(scale);
                    image.setScaleY(scale);
                }
                return true;
            } else if (id == R.id.cab_decrease_size) {
                for (ImageView image : selectedImages) {
                    float scale = originalScales.get(image);
                    if (scale > 0.4f) {
                        scale -= 0.2f;
                        originalScales.put(image, scale);
                        image.setScaleX(scale);
                        image.setScaleY(scale);
                    }
                }
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (ImageView image : selectedImages) {
                image.setAlpha(1.0f);
            }
            selectedImages.clear();
            actionMode = null;
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle(R.string.select_action);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (lastContextImage == null) return super.onContextItemSelected(item);

        ImageView selectedImage = lastContextImage;
        int id = item.getItemId();

        if (id == R.id.context_delete) {
            selectedImage.setVisibility(View.INVISIBLE);
            return true;
        } else if (id == R.id.context_increase_size) {
            float scale = originalScales.get(selectedImage);
            scale += 0.2f;
            originalScales.put(selectedImage, scale);
            selectedImage.setScaleX(scale);
            selectedImage.setScaleY(scale);
            return true;
        } else if (id == R.id.context_decrease_size) {
            float scale = originalScales.get(selectedImage);
            if (scale > 0.4f) {
                scale -= 0.2f;
                originalScales.put(selectedImage, scale);
                selectedImage.setScaleX(scale);
                selectedImage.setScaleY(scale);
            }
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            item.setTitle(null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_restore) {
            restoreAllImages();
            return true;
        } else if (id == R.id.menu_delete_all) {
            deleteAllImages();
            return true;
        } else if (id == R.id.menu_animal_selector) {
            showPopupMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu() {
        View menuView = findViewById(R.id.menu_animal_selector);
        if (menuView == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            menuView = toolbar;
        }

        PopupMenu popup = new PopupMenu(this, menuView);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_cats) {
                loadAnimalImages("cats");
                Toast.makeText(this, R.string.cats, Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_dogs) {
                loadAnimalImages("dogs");
                Toast.makeText(this, R.string.dogs, Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_birds) {
                loadAnimalImages("birds");
                Toast.makeText(this, R.string.birds, Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_rabbits) {
                loadAnimalImages("rabbits");
                Toast.makeText(this, R.string.rabbits, Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_hamsters) {
                loadAnimalImages("hamsters");
                Toast.makeText(this, R.string.hamsters, Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });

        popup.show();
    }

    private void toggleLanguage() {
        isUkrainian = !isUkrainian;
        Locale newLocale = isUkrainian ? new Locale("uk") : new Locale("en");
        Locale.setDefault(newLocale);

        Configuration config = new Configuration();
        config.locale = newLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        Toast.makeText(this, isUkrainian ? R.string.language_changed : R.string.language_changed_en, Toast.LENGTH_SHORT).show();
        recreate();

        if (actionMode != null) {
            actionMode.setTitle(getString(R.string.selected));
        }
    }

    private void restoreAllImages() {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.VISIBLE);
            float scale = 1.0f;
            originalScales.put(imageView, scale);
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
        }

        loadAnimalImages(currentAnimalType);
        Toast.makeText(this, R.string.images_restored, Toast.LENGTH_SHORT).show();
    }

    private void deleteAllImages() {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.INVISIBLE);
        }

        Toast.makeText(this, R.string.images_deleted, Toast.LENGTH_SHORT).show();
    }
}
