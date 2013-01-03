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

import com.androidformenhancer.annotation.AlphaNum;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Test case for AlphaNumValidator.<br>
 * Include AndroidFormEnhancer project as library and run as Android JUnit test.
 * 
 * @author Soichiro Kashima
 */
public class AlphaNumValidatorTest extends InstrumentationTestCase {

    /**
     * Dummy class which has @AlphaNum field.
     */
    public class Foo {
        @AlphaNum
        public String a;

        @AlphaNum(allowSpace = true)
        public String b;
    }

    public void testValidate() throws Exception {
        Foo foo = new Foo();

        AlphaNumValidator validator = new AlphaNumValidator();
        validator.setContext(getInstrumentation().getContext());
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

        foo.a = " ";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        foo.a = "　";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        foo.a = "a";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        foo.a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        foo.a = "1";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNull(errorMessage);

        foo.a = "あ";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        foo.a = "ア";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        foo.a = "亜";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.a + ", message: " + errorMessage);
        assertNotNull(errorMessage);

        field = Foo.class.getDeclaredField("b");

        foo.b = " ";
        errorMessage = validator.validate(field);
        Log.i("TEST", "input: " + foo.b + ", message: " + errorMessage);
        assertNull(errorMessage);
    }

}
