# Image Cropper App (MVI Architecture)

## 📌 Overview

This Android application provides an intuitive **image cropping** experience using **Jetpack Compose
**. It follows a **modern MVI (Model-View-Intent) architecture** with a delegated approach to manage
state, events, and side effects efficiently.

## 🏗 Architecture

The project is structured using the **MVI pattern**, ensuring a clean separation of concerns:

- **MVI Interface (![MVI](app/src/main/java/com/bj/image_cropper/core/mvi/MVI.kt))**: Defines a
  contract for managing UI state, events, and side effects.
- **MVI Delegate (![MVI Delegation](app/src/main/java/com/bj/image_cropper/core/mvi/MVIDelegate.kt))
  **: Implements the MVI contract, handling state updates and emitting
  side effects.
- **Composable Side Effect Collector (`CollectSideEffect`)**: Observes and reacts to side effects in
  a lifecycle-aware manner.
- **ViewModel (`HomeViewModel`)**: Implements MVI using delegation and manages UI logic.
- **Contract (`HomeContract`)**: Defines the `UiState`, `UiAction`, and `SideEffect` for the screen.

### 🔹 MVI Implementation

The ![MVI](app/src/main/java/com/bj/image_cropper/core/mvi/MVI.kt) interface abstracts state
management,
and ![MVI Delegation](app/src/main/java/com/bj/image_cropper/core/mvi/MVIDelegate.kt) provides its
implementation, ensuring modularity and reusability.

## ✂ Image Cropping Feature

The **CropperPreview** composable enables smooth image cropping with essential features:

- **Auto Zoom**: Automatically adjusts the crop region to fit the view.
- **Touch Gestures**: Supports dragging, resizing, and scaling.
- **Matrix Transformations**: Ensures accurate image positioning and cropping.

### 🔹 Key Components

-
    *
*![CropperPreview](app/src/main/java/com/bj/image_cropper/feature/cropper/presentation/ui/component/CropperPreview.kt)
**: The main composable for rendering and handling image cropping.
- **`BringToView`**: Ensures the cropped area remains visible by adjusting the view matrix.
- **State Handling (`CropState`)**: Stores transformation, cropping region, and user interactions.

## 🚀 How to Use

1. **Select an image** from the gallery.
2. **Crop the image** using touch gestures.
3. **Proceed with the edited image** or navigate to another screen.

## 📌 Tech Stack

- **Jetpack Compose** for UI
- **Kotlin** for logic
- **StateFlow & Coroutines** for reactive state management

## 🎯 Conclusion

This project offers a highly interactive **image cropping experience** with a structured **MVI
architecture**, ensuring scalability and maintainability.

