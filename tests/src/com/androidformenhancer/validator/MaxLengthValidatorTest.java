/*
 * Copyright 2012 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidformenhancer.validator;

import com.androidformenhancer.form.annotation.MaxLength;
import com.androidformenhancer.test.DummyActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Test case for MaxLengthValidator.<br>
 * Include AndroidFormEnhancer project as library and run as Android JUnit test.
 * 
 * @author Soichiro Kashima
 */
public class MaxLengthValidatorTest extends ActivityInstrumentationTestCase2<DummyActivity> {

    public MaxLengthValidatorTest() {
        super(DummyActivity.class);
    }

    public MaxLengthValidatorTest(Class<DummyActivity> activityClass) {
        super(activityClass);
    }

    /**
     * Dummy class which has @Digits field.
     */
    public class Foo {
        @MaxLength(5)
        public String a;
    }

    public void testValidate() throws Exception {
        Foo foo = new Foo();

        MaxLengthValidator validator = new MaxLengthValidator();
        validator.setContext(getActivity());
        validator.setTarget(foo);
        Field field = Foo.class.getDeclaredField("a");

        // Null
        foo.a = null;
        String errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Empty
        foo.a = "";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // 1 character with ASCII
        foo.a = "a";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // 2 characters with ASCII
        foo.a = "ab";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Boundary with ASCII
        foo.a = "abcde";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Boundary +1 with ASCII
        foo.a = "abcdef";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        // 1 character with Multi-byte
        foo.a = "あ";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // 2 characters with Multi-byte
        foo.a = "あい";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Boundary with Multi-byte
        foo.a = "あいうえお";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Boundary +1 with Multi-byte
        foo.a = "あいうえおか";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        // Boundary with Multi-byte including surrogate pair
        foo.a = "あいう𠮷";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        // Boundary +1 with Multi-byte including surrogate pair
        foo.a = "あいうえ𠮷";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);
    }

}