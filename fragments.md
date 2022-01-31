# TicTacToe - Fragments

1. Create a new branch called `fragments`
    1. Check out the new create branch
2. Create a Layout called `activity_fragments`
    1. Add a `FragmentContainerView` to the layout
    2. Give it an ID i.e. `fragment_holder`
    3. Let the holder fill the whole space
3. Create a new Activity called `FragmentsActivity`
    1. Extend `AppCompatActivity`
    2. Create a `binding` variable and initialise it by using the `ActivityFragmentBinding`
4. Go to the `AndroidManifest`
    1. Inside the `<activity>` tag replace the name to `.FragmentsActivity`
5. Create a Layout called `fragment_title`
    1. Add a `TextView` with the AppName as text
    2. Add a `Button` with the text: „Start Game“
6. Create a new Class called `TitleFragment`
    1. Extend `Fragment`
    2. Create a class-variable called `onButtonClick`
        1. The type is a lambda (i.e. -> `() -> Unit`)
        2. It’s a mutable variable
        3. The variable is initialised as lambda without a functionality -> `{}`
    3. Use `viewBinding` to inflate the Layout `fragment_title`
    4. Add a onClickListener to the Button
        1. Inside the lambda call the class lambda `onButtonClick()`
7. Add the attribute `name` of the `TitleFragment` to the `FragmentContainerView` of the `activity_fragments` layout
8. Create a Layout called `fragment_game`
    1. Copy the content from the `activity_main` layout
9. Create a new class called `GameFragment`
    1. Use `viewBinding` to inflate the layout
10. Go to the `FragmentsActivity`
    1. In the `onCreate` function create a variable `currentFragment`
    2. Assign the current Fragment to the variable
        1. Use the `supportFragmentManager`
        2. Call the `findFragmentById` function of the `supportFragmentManager`
        3. The parameter for the function is the id of the `FragmentContainerView`
    3. Check if the `currentFragment` is of type `TitleFragment`
        1. If the condition is true assign a value to the `onButtonClick` variable of the `currentFragment`
        2. The value should be a lambda
        3. Use the `supportFragmentManager` to replace the content of the `fragment_holder` with the `GameFragment`
