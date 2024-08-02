package com.torpill.timothosaurus.items.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DisplayModel extends Model {
    private final Map<ModelPosition, ModelTransformation> transformations = new HashMap<>();

    public DisplayModel(Optional<Identifier> parent, Optional<String> variant, TextureKey... requiredTextureKeys) {
        super(parent, variant, requiredTextureKeys);
    }

    public ModelTransformation getOrCreateTransformation(ModelPosition position) {
        if (!transformations.containsKey(position)) {
            transformations.put(position, new ModelTransformation());
        }
        return transformations.get(position);
    }

    @Override
    public JsonObject createJson(Identifier id, Map<TextureKey, Identifier> textures) {
        JsonObject jsonObject = super.createJson(id, textures);
        if (!transformations.isEmpty()) {
            jsonObject.add("display", createDisplayJson());
        }
        return jsonObject;
    }

    private JsonObject createDisplayJson() {
        JsonObject jsonObject = new JsonObject();
        transformations.forEach((position, transformation) -> jsonObject.add(position.toString(), transformation.createJson()));
        return jsonObject;
    }

    public enum ModelPosition {
        THIRD_PERSON_RIGHT_HAND("thirdperson_righthand"),
        THIRD_PERSON_LEFT_HAND("thirdperson_lefthand"),
        FIRST_PERSON_RIGHT_HAND("firstperson_righthand"),
        FIRST_PERSON_LEFT_HAND("firstperson_lefthand"),
        GUI("gui"),
        HEAD("head"),
        GROUND("ground"),
        FIXED("fixed"),
        ;

        private final String position;

        ModelPosition(String position) {
            this.position = position;
        }

        @Override
        public String toString() {
            return position;
        }
    }

    public class ModelTransformation {
        private Vector3f rotation;
        private Vector3f translation;
        private Vector3f scale;

        public ModelTransformation() {
        }

        public ModelTransformation rotation(@NotNull Vector3f rotation) {
            this.rotation = new Vector3f(rotation);
            return this;
        }

        public ModelTransformation rotation(float x, float y, float z) {
            this.rotation = new Vector3f(x, y, z);
            return this;
        }

        public ModelTransformation translation(@NotNull Vector3f translation) {
            this.translation = new Vector3f(translation);
            return this;
        }

        public ModelTransformation translation(float x, float y, float z) {
            this.translation = new Vector3f(x, y, z);
            return this;
        }

        public ModelTransformation scale(@NotNull Vector3f scale) {
            this.scale = new Vector3f(scale);
            return this;
        }

        public ModelTransformation scale(float x, float y, float z) {
            this.scale = new Vector3f(x, y, z);
            return this;
        }

        public Optional<Vector3f> getRotation() {
            return Optional.ofNullable(rotation);
        }

        public Optional<Vector3f> getTranslation() {
            return Optional.ofNullable(translation);
        }

        public Optional<Vector3f> getScale() {
            return Optional.ofNullable(scale);
        }

        public DisplayModel submit() {
            return DisplayModel.this;
        }

        public JsonObject createJson() {
            JsonObject jsonObject = new JsonObject();
            getRotation().ifPresent(rotation -> jsonObject.add("rotation", createJsonArray(rotation)));
            getTranslation().ifPresent(translation -> jsonObject.add("translation", createJsonArray(translation)));
            getScale().ifPresent(scale -> jsonObject.add("scale", createJsonArray(scale)));
            return jsonObject;
        }

        private JsonArray createJsonArray(Vector3f vec) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(vec.x());
            jsonArray.add(vec.y());
            jsonArray.add(vec.z());
            return jsonArray;
        }
    }
}
