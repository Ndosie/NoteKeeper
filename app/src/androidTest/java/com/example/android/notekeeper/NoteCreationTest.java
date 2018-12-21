package com.example.android.notekeeper;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
    static DataManager sDataManager;

    @BeforeClass
    public static void classSetup(){
        sDataManager = DataManager.getInstance();
    }
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        final CourseInfo course = sDataManager.getCourse("java_lang");
        final String noteTitle = "Note Title";
        final String noteText = "Note text";

//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class),equalTo(course))).perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(containsString(
                course.getTitle()))));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                            .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.text_note_text)).perform(typeText(noteText), closeSoftKeyboard());
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));

        pressBack();

        int noteIndex = sDataManager.getNotes().size() - 1;
        NoteInfo noteInfo = sDataManager.getNotes().get(noteIndex);
        assertEquals(course, noteInfo.getCourse());
        assertEquals(noteTitle, noteInfo.getTitle());
        assertEquals(noteText, noteInfo.getText());
    }
}