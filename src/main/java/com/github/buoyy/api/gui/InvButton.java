package com.github.buoyy.api.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Represents a button in the GUI. A classic way
 * of using the inventory based GUIs is using such
 * buttons to either run an internal function or open another GUI.
 * This class uses Builder pattern to build objects.
 * See the GitHub page for more.
 */
@SuppressWarnings("unused")
public class InvButton {
    private final ItemStack icon;
    private final Consumer<InventoryClickEvent> onClick;

    private InvButton(Builder builder) {
        this.icon = builder.icon;
        this.onClick = builder.onClick;
    }

    /**
     * Returns the item stack which is used for this display
     * of this button in the inventory.
     * @return The item stack displayed
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Returns the Consumer (lambda function) associated with a
     * regular click on this button.
     * @return The Consumer i.e. lambda to run on click
     */
    public Consumer<InventoryClickEvent> getOnClick() {
        return onClick;
    }

    /**
     * The inner Builder class associated with this class.
     * Used for making InvButton objects gracefully.
     */
    public static class Builder {
        private ItemStack icon;
        private Consumer<InventoryClickEvent> onClick;

        /**
         * Returns a new Builder instance.
         * Must be the first method run before building
         * an InvButton object.
         * @return The new Builder
         */
        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * Returns the builder with an item display as provided
         * in the argument
         * @param icon The Material whose texture will be used
         *             for the button
         * @return The Builder object
         */
        public Builder setIcon(Material icon) {
            this.icon = new ItemStack(icon);
            return this;
        }

        /**
         * Returns the builder with an item name as provided
         * in the argument. If not used, the button's name will
         * be the same as its icon.
         * @param name The name for the button
         * @return The Builder object
         */
        public Builder setName(String name) {
            ItemMeta meta = this.icon.getItemMeta();
            assert meta != null;
            meta.setDisplayName(name);
            this.icon.setItemMeta(meta);
            return this;
        }

        /**
         * Returns the builder with an item lore as provided in
         * the argument. If not used, the button's lore will not exist.
         * @param lore The string array which will be the lore
         *             for the button
         * @return The Builder object
         */
        public Builder setLore(String... lore) {
            ItemMeta meta = this.icon.getItemMeta();
            assert meta != null;
            meta.setLore(Arrays.asList(lore));
            this.icon.setItemMeta(meta);
            return this;
        }

        /**
         * Returns the builder with an on click Consumer(lambda
         * function) as provided in the argument. If not used,
         * the button will have no function running when it is clicked,
         * as if it was a defunct button.
         * @param onClick The Consumer(lambda) to run for the button
         *                when it clicked.
         * @return The Builder object.
         */
        public Builder setOnClick(Consumer<InventoryClickEvent> onClick) {
            this.onClick = onClick;
            return this;
        }

        /**
         * Builds the button with the provided fields.
         * This method is to be run at the end of your button
         * building process.
         * @return The InvButton that was built.
         */
        public InvButton build() {
            return new InvButton(this);
        }
    }
}