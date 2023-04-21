from kivy.app import App
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.uix.boxlayout import BoxLayout

class MyApp(App):
    def build(self):
        layout = BoxLayout(orientation='vertical')
        label = Label(text="Hello, World!")
        button = Button(text="Click me!")

        button.bind(on_press=self.on_button_press)

        layout.add_widget(label)
        layout.add_widget(button)

        return layout

    def on_button_press(self, instance):
        instance.text = "You clicked me!"

if __name__ == '__main__':
    MyApp().run()
