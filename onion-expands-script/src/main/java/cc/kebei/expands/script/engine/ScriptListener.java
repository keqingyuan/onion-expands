package cc.kebei.expands.script.engine;

/**
 * Created by Kebei on 16-6-27.
 */
public interface ScriptListener {
    void before(ScriptContext context);

    void after(ScriptContext context, ExecuteResult result);
}
