import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {

    private DoublyLinkedList<Integer> list;
    private TextArea outputArea;
    private TextField valueField;
    private TextField indexField;
    private Pane visualPane;

    @Override
    public void start(Stage primaryStage) {
        list = new DoublyLinkedList<>();

        // Главный контейнер
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Заголовок
        Label titleLabel = new Label("Обработка двусвязных списков");
        titleLabel.setFont(new Font("Times New Roman", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));
        root.setTop(titleBox);

        // Панель ввода и кнопок
        VBox controlPanel = createControlPanel();
        root.setLeft(controlPanel);

        // Панель визуализации
        VBox visualPanel = createVisualPanel();
        root.setCenter(visualPanel);

        // Панель вывода
        VBox outputPanel = createOutputPanel();
        root.setRight(outputPanel);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Двусвязный список - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateVisualization();
    }

    /**
     * Создание панели управления с кнопками
     */
    private VBox createControlPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(280);
        panel.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label inputLabel = new Label("Панель управления");
        inputLabel.setFont(new Font("Times New Roman", 16));
        inputLabel.setStyle("-fx-font-weight: bold;");

        // Поле ввода значения
        Label valueLabel = new Label("Значение:");
        valueField = new TextField();
        valueField.setPromptText("Введите число");

        // Поле ввода индекса
        Label indexLabel = new Label("Индекс:");
        indexField = new TextField();
        indexField.setPromptText("Введите индекс");

        // Кнопки операций
        Button addFirstBtn = createStyledButton("Добавить в начало", "#2196F3");
        addFirstBtn.setOnAction(e -> addFirst());

        Button addLastBtn = createStyledButton("Добавить в конец", "#2196F3");
        addLastBtn.setOnAction(e -> addLast());

        Button addAtIndexBtn = createStyledButton("Добавить по индексу", "#2196F3");
        addAtIndexBtn.setOnAction(e -> addAtIndex());

        Button removeFirstBtn = createStyledButton("Удалить первый", "#2196F3");
        removeFirstBtn.setOnAction(e -> removeFirst());

        Button removeLastBtn = createStyledButton("Удалить последний", "#2196F3");
        removeLastBtn.setOnAction(e -> removeLast());

        Button removeAtIndexBtn = createStyledButton("Удалить по индексу", "#2196F3");
        removeAtIndexBtn.setOnAction(e -> removeAtIndex());

        Button removeByValueBtn = createStyledButton("Удалить по значению", "#2196F3");
        removeByValueBtn.setOnAction(e -> removeByValue());

        Button findBtn = createStyledButton("Найти элемент", "#2196F3");
        findBtn.setOnAction(e -> findElement());

        Button getBtn = createStyledButton("Получить по индексу", "#2196F3");
        getBtn.setOnAction(e -> getElement());

        Button clearBtn = createStyledButton("Очистить список", "#2196F3");
        clearBtn.setOnAction(e -> clearList());

        Separator separator = new Separator();

        Button printForwardBtn = createStyledButton("Прямой обход", "#2196F3");
        printForwardBtn.setOnAction(e -> printForward());

        Button printBackwardBtn = createStyledButton("Обратный обход", "#2196F3");
        printBackwardBtn.setOnAction(e -> printBackward());

        panel.getChildren().addAll(
                inputLabel,
                new Separator(),
                valueLabel, valueField,
                indexLabel, indexField,
                new Separator(),
                addFirstBtn, addLastBtn, addAtIndexBtn,
                removeFirstBtn, removeLastBtn, removeAtIndexBtn,
                removeByValueBtn, findBtn, getBtn,
                separator,
                printForwardBtn, printBackwardBtn, clearBtn);

        return panel;
    }

    /**
     * Создание стилизованной кнопки
     */
    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 8px; " +
                        "-fx-background-radius: 0; " +
                        "-fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: derive(" + color + ", -10%); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 8px; " +
                        "-fx-background-radius: 0; " +
                        "-fx-cursor: hand;"));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 8px; " +
                        "-fx-background-radius: 0; " +
                        "-fx-cursor: hand;"));

        return btn;
    }

    /**
     * Создание панели визуализации
     */
    private VBox createVisualPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);

        Label visualLabel = new Label("Показ списка");
        visualLabel.setFont(new Font("Times New Roman", 16));
        visualLabel.setStyle("-fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        visualPane = new Pane();
        visualPane.setMinHeight(500);
        visualPane.setStyle("-fx-background-color: #ecf0f1;");

        scrollPane.setContent(visualPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        panel.getChildren().addAll(visualLabel, scrollPane);

        return panel;
    }

    /**
     * Создание панели вывода
     */
    private VBox createOutputPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(300);
        panel.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label outputLabel = new Label("Вывод операций");
        outputLabel.setFont(new Font("Times New Roman", 16));
        outputLabel.setStyle("-fx-font-weight: bold;");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefRowCount(30);
        outputArea.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 12px;");
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        panel.getChildren().addAll(outputLabel, outputArea);

        return panel;
    }

    /**
     * Визуализация двусвязного списка
     */
    private void updateVisualization() {
        visualPane.getChildren().clear();

        if (list.isEmpty()) {
            Text emptyText = new Text("Список пуст");
            emptyText.setFont(new Font("Times New Roman", 18));
            emptyText.setFill(Color.GRAY);
            emptyText.setX(visualPane.getWidth() / 2 - 50);
            emptyText.setY(visualPane.getHeight() / 2);
            visualPane.getChildren().add(emptyText);
            return;
        }

        int nodeRadius = 30;
        int nodeSpacing = 120;
        int startX = 60;
        int startY = 100;

        // Рисуем узлы и связи
        for (int i = 0; i < list.size(); i++) {
            int x = startX + i * nodeSpacing;
            int y = startY;

            // Круг узла
            Circle circle = new Circle(x, y, nodeRadius);
            circle.setFill(Color.web("#3498db"));
            circle.setStroke(Color.web("#2980b9"));
            circle.setStrokeWidth(3);

            // Значение узла
            Text valueText = new Text(String.valueOf(list.get(i)));
            valueText.setFont(new Font("Times New Roman", 20));
            valueText.setFill(Color.WHITE);
            valueText.setStyle("-fx-font-weight: bold;");

            // Центрируем текст
            valueText.setX(x - valueText.getLayoutBounds().getWidth() / 2);
            valueText.setY(y + valueText.getLayoutBounds().getHeight() / 4);

            // Индекс узла
            Text indexText = new Text("[" + i + "]");
            indexText.setFont(new Font("Times New Roman", 12));
            indexText.setFill(Color.DARKGRAY);
            indexText.setX(x - indexText.getLayoutBounds().getWidth() / 2);
            indexText.setY(y + nodeRadius + 20);

            visualPane.getChildren().addAll(circle, valueText, indexText);

            // Рисуем стрелки к следующему узлу
            if (i < list.size() - 1) {
                int nextX = startX + (i + 1) * nodeSpacing;

                // Стрелка вперед (сверху)
                Line forwardLine = new Line(x + nodeRadius, y - 10, nextX - nodeRadius, y - 10);
                forwardLine.setStroke(Color.web("#2ecc71"));
                forwardLine.setStrokeWidth(2);

                // Наконечник стрелки вперед
                Line arrowHead1 = new Line(nextX - nodeRadius, y - 10, nextX - nodeRadius - 10, y - 15);
                arrowHead1.setStroke(Color.web("#2ecc71"));
                arrowHead1.setStrokeWidth(2);

                Line arrowHead2 = new Line(nextX - nodeRadius, y - 10, nextX - nodeRadius - 10, y - 5);
                arrowHead2.setStroke(Color.web("#2ecc71"));
                arrowHead2.setStrokeWidth(2);

                // Стрелка назад (снизу)
                Line backwardLine = new Line(nextX - nodeRadius, y + 10, x + nodeRadius, y + 10);
                backwardLine.setStroke(Color.web("#e74c3c"));
                backwardLine.setStrokeWidth(2);

                // Наконечник стрелки назад
                Line arrowHead3 = new Line(x + nodeRadius, y + 10, x + nodeRadius + 10, y + 5);
                arrowHead3.setStroke(Color.web("#e74c3c"));
                arrowHead3.setStrokeWidth(2);

                Line arrowHead4 = new Line(x + nodeRadius, y + 10, x + nodeRadius + 10, y + 15);
                arrowHead4.setStroke(Color.web("#e74c3c"));
                arrowHead4.setStrokeWidth(2);

                visualPane.getChildren().addAll(
                        forwardLine, arrowHead1, arrowHead2,
                        backwardLine, arrowHead3, arrowHead4);
            }

            // Метки HEAD и TAIL
            if (i == 0) {
                Text headLabel = new Text("HEAD");
                headLabel.setFont(new Font("Times New Roman", 14));
                headLabel.setFill(Color.web("#2ecc71"));
                headLabel.setStyle("-fx-font-weight: bold;");
                headLabel.setX(x - headLabel.getLayoutBounds().getWidth() / 2);
                headLabel.setY(y - nodeRadius - 10);
                visualPane.getChildren().add(headLabel);
            }

            if (i == list.size() - 1) {
                Text tailLabel = new Text("TAIL");
                tailLabel.setFont(new Font("Times New Roman", 14));
                tailLabel.setFill(Color.web("#e74c3c"));
                tailLabel.setStyle("-fx-font-weight: bold;");
                tailLabel.setX(x - tailLabel.getLayoutBounds().getWidth() / 2);
                tailLabel.setY(y - nodeRadius - 10);
                visualPane.getChildren().add(tailLabel);
            }
        }

        // Информация о размере
        Text sizeInfo = new Text("Размер списка: " + list.size());
        sizeInfo.setFont(new Font("Times New Roman", 16));
        sizeInfo.setFill(Color.web("#34495e"));
        sizeInfo.setStyle("-fx-font-weight: bold;");
        sizeInfo.setX(20);
        sizeInfo.setY(30);
        visualPane.getChildren().add(sizeInfo);
    }

    // Обработчики операций

    private void addFirst() {
        try {
            int value = Integer.parseInt(valueField.getText());
            list.addFirst(value);
            logOperation("✓ Добавлен элемент " + value + " в начало списка");
            updateVisualization();
            valueField.clear();
        } catch (NumberFormatException e) {
            showError("Введите корректное число");
        }
    }

    private void addLast() {
        try {
            int value = Integer.parseInt(valueField.getText());
            list.addLast(value);
            logOperation("✓ Добавлен элемент " + value + " в конец списка");
            updateVisualization();
            valueField.clear();
        } catch (NumberFormatException e) {
            showError("Введите корректное число");
        }
    }

    private void addAtIndex() {
        try {
            int value = Integer.parseInt(valueField.getText());
            int index = Integer.parseInt(indexField.getText());
            list.add(index, value);
            logOperation("✓ Добавлен элемент " + value + " на позицию " + index);
            updateVisualization();
            valueField.clear();
            indexField.clear();
        } catch (NumberFormatException e) {
            showError("Введите корректные числа для значения и индекса");
        } catch (IndexOutOfBoundsException e) {
            showError(e.getMessage());
        }
    }

    private void removeFirst() {
        try {
            int removed = list.removeFirst();
            logOperation("✓ Удален первый элемент: " + removed);
            updateVisualization();
        } catch (IllegalStateException e) {
            showError(e.getMessage());
        }
    }

    private void removeLast() {
        try {
            int removed = list.removeLast();
            logOperation("✓ Удален последний элемент: " + removed);
            updateVisualization();
        } catch (IllegalStateException e) {
            showError(e.getMessage());
        }
    }

    private void removeAtIndex() {
        try {
            int index = Integer.parseInt(indexField.getText());
            int removed = list.remove(index);
            logOperation("✓ Удален элемент на позиции " + index + ": " + removed);
            updateVisualization();
            indexField.clear();
        } catch (NumberFormatException e) {
            showError("Введите корректный индекс");
        } catch (IndexOutOfBoundsException e) {
            showError(e.getMessage());
        }
    }

    private void removeByValue() {
        try {
            int value = Integer.parseInt(valueField.getText());
            boolean removed = list.removeByValue(value);
            if (removed) {
                logOperation("✓ Удален элемент со значением: " + value);
            } else {
                logOperation("✗ Элемент " + value + " не найден");
            }
            updateVisualization();
            valueField.clear();
        } catch (NumberFormatException e) {
            showError("Введите корректное число");
        }
    }

    private void findElement() {
        try {
            int value = Integer.parseInt(valueField.getText());
            int index = list.indexOf(value);
            if (index != -1) {
                logOperation("✓ Элемент " + value + " найден на позиции: " + index);
            } else {
                logOperation("✗ Элемент " + value + " не найден в списке");
            }
        } catch (NumberFormatException e) {
            showError("Введите корректное число");
        }
    }

    private void getElement() {
        try {
            int index = Integer.parseInt(indexField.getText());
            int value = list.get(index);
            logOperation("✓ Элемент на позиции " + index + ": " + value);
        } catch (NumberFormatException e) {
            showError("Введите корректный индекс");
        } catch (IndexOutOfBoundsException e) {
            showError(e.getMessage());
        }
    }

    private void clearList() {
        list.clear();
        logOperation("✓ Список очищен");
        updateVisualization();
    }

    private void printForward() {
        if (list.isEmpty()) {
            logOperation("Список пуст");
            return;
        }

        StringBuilder sb = new StringBuilder("Прямой обход: ");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(" <-> ");
            }
        }
        logOperation(sb.toString());
    }

    private void printBackward() {
        if (list.isEmpty()) {
            logOperation("Список пуст");
            return;
        }

        StringBuilder sb = new StringBuilder("Обратный обход: ");
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
            if (i > 0) {
                sb.append(" <-> ");
            }
        }
        logOperation(sb.toString());
    }

    private void logOperation(String message) {
        outputArea.appendText(message + "\n");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
