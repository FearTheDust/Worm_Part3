package worms.model.program;

import java.util.List;
import worms.gui.game.IActionHandler;
import worms.model.Entity;
import worms.model.Program;
import worms.model.program.exceptions.IllegalArgException;
import worms.model.program.exceptions.IllegalTypeException;
import worms.model.program.statements.*;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;
import worms.model.Food;
import worms.model.Worm;
import worms.util.Util;

/**
 * The class implementing the ProgramFactory which handles our design.
 * 
 * When we want to execute the program build by this programFactory we have to make sure we set the worm (this.setWorm(Worm)) right BEFORE
 * we try to execute the program. Also, every worm requires a new factory.
 * 
 * When a wrong type of expression or .. is presented an exception will be thrown.
 * This is not stated in the function itself since we don't allow anyone to catch them as we want the program to terminate.
 *
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class ProgramFactoryImpl implements ProgramFactory<Expression, Statement, Variable> {
    
    /**
     * The programFactory to help construct the Program.
     * @param handler The handler to execute the statements with.
     */
    public ProgramFactoryImpl(IActionHandler handler) {
        this.handler = handler;
    }
    
    private final IActionHandler handler;
    
    /**
     * Set the program parser for this ProgramFactoryImpl.
     * @param parser The parser to parse with.
     * @throws IllegalArgumentException
     *          When the parser isn't using this factory.
     */
    public void setProgramParser(ProgramParser<Expression, Statement, Variable> parser) throws IllegalArgumentException {
        if(parser.getFactory() == this)
            this.parser = parser;
        else
            throw new IllegalArgumentException("The ProgramParser must have the factory set to this factory when setting it as.");
    }
    
    
    public ProgramParser<Expression, Statement, Variable> getProgramParser() {
        return this.parser;
    }
    
    /**
     * Set the worm for the factory to execute the statements for.
     * @param worm The worm where this Program is made for.
     */
    public void setWorm(Worm worm) {
        if(this.worm != null)
            throw new IllegalStateException("This factory already has a worm set.");
        
        this.worm = worm;
    }
    
    private Worm worm;
    private ProgramParser<Expression, Statement, Variable> parser;
    
    @Override
    public DoubleExpression createDoubleLiteral(int line, int column, final double d) {
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return d;
            }
        };
    }

    @Override
    public BooleanExpression createBooleanLiteral(int line, int column, final boolean b) {
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return b;
            }
        };
    }

    @Override
    public BooleanExpression createAnd(final int line, final int column, final Expression e1, final Expression e2) {
        if (!e1.getType().isAssignableFrom(Boolean.class) || !e2.getType().isAssignableFrom(Boolean.class))
            throw new IllegalTypeException(line, column, "An &&-expression must consist of atleast 2 boolean types.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Boolean) e1.getResult() && (Boolean) e2.getResult();
            }
        };
    }

    @Override
    public BooleanExpression createOr(final int line, final int column, final Expression e1, final Expression e2) {
      if (!e1.getType().isAssignableFrom(Boolean.class) || !e2.getType().isAssignableFrom(Boolean.class))
          throw new IllegalTypeException(line, column, "An ||-expression must consist of atleast 2 boolean types.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
               return (Boolean) e1.getResult() || (Boolean) e2.getResult();
            }
        };
    }

    @Override
    public BooleanExpression createNot(final int line, final int column, final Expression e) {
        if (!e.getType().isAssignableFrom(Boolean.class))
            throw new IllegalTypeException(line, column, "An !-expression must consist of a boolean type.");
       
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return !((Boolean) e.getResult());
            }
        };
    }

    @Override
    public EntityExpression createNull(int line, int column) {
        return new EntityExpression() {
            @Override
            public Entity getResult() {
                return null;
            }
        };
    }

    @Override
    public EntityExpression createSelf(int line, int column) {
        return new EntityExpression() {
            @Override
            public Entity getResult() {
                if(ProgramFactoryImpl.this.getWorm() == null)
                    throw new IllegalStateException("The ProgramFactory has to have its worm set before we try to execute any statements created by the factory.");
                return worm;
            }
        };
    }

    @Override
    public DoubleExpression createGetX(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                //can throw NullPointerException/NotSupported/.. when necessary :)
                return ((Entity) e.getResult()).getPosition().getX();
            }
        };
    }

    @Override
    public DoubleExpression createGetY(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getPosition().getY();
            }
        };
    }

    @Override
    public DoubleExpression createGetRadius(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getRadius();
            }
        };
    }

    @Override
    public DoubleExpression createGetDir(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getAngle();
            }
        };
    }

    @Override
    public DoubleExpression createGetAP(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxAP(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getMaxAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetHP(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getHP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxHP(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((Entity) e.getResult()).getMaxHP();
            }
        };
    }

    @Override
    public BooleanExpression createSameTeam(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return getWorm().getTeam() == ((Entity) e.getResult()).getTeam() && getWorm().getTeam() != null;
            }
        };
    }

    @Override
    public EntityExpression createSearchObj(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The parameter must be a Double type.");
        
        return new EntityExpression() {
            @Override
            public Entity getResult() {
                Worm worm = ProgramFactoryImpl.this.getWorm();
                return worm.getWorld().searchObject(worm.getPosition(), worm.getAngle() + (double) e.getResult());
            }
        };
    }

    @Override
    public BooleanExpression createIsWorm(final int line, final int column, final Expression e) {
        if (!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return e.getResult() instanceof Worm;
            }
        };
    }

    @Override
    public BooleanExpression createIsFood(final int line, final int column, final Expression e) {
        if (!e.getType().isAssignableFrom(Entity.class))
            throw new IllegalTypeException(line, column, "The parameter must be an Entity type.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return e.getResult() instanceof Food;
            }
        };
    }

    @Override
    public VariableExpression createVariableAccess(final int line, final int column, final String name) {
        return null;
        //return new VariableExpression(this.getProgramParser(), name, line, column);
    }
    
    @Override
    public VariableExpression createVariableAccess(int line, int column, String name, Variable type) {
        return new VariableExpression(line, column, name, type);
    }

    @Override
    public BooleanExpression createLessThan(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Double) e1.getResult() < (Double) e2.getResult();
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThan(final int line, final int column, final Expression e1, final Expression e2) {
         if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Double) e1.getResult() > (Double) e2.getResult();
            }
        };
    }
    
    

    @Override
    public BooleanExpression createLessThanOrEqualTo(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyLessThanOrEqualTo((Double) e1.getResult(), (Double) e2.getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThanOrEqualTo(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyGreaterThanOrEqualTo((Double) e1.getResult(), (Double) e2.getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createEquality(final int line, final int column, final Expression e1, final Expression e2) {
        if(e1.getType() != e2.getType())
            throw new IllegalTypeException(line, column, "Both expressions must have the same type.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (e1.getResult() == e2.getResult());
            }
        };
    }

    @Override
    public BooleanExpression createInequality(int line, int column, Expression e1, Expression e2) {
        return this.createNot(line, column, this.createEquality(line, column, e1, e2));
    }

    @Override
    public DoubleExpression createAdd(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((double) e1.getResult()) + ((double) e2.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSubtraction(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((double) e1.getResult()) - ((double) e2.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createMul(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((double) e1.getResult()) * ((double) e2.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createDivision(final int line, final int column, final Expression e1, final Expression e2) {
        if(!e1.getType().isAssignableFrom(Double.class) || !e2.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((double) e1.getResult()) / ((double) e2.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSqrt(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The argument must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sqrt((Double) e.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSin(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The argument must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sin((Double) e.getResult());
            }
        };
    }

    @Override
    public DoubleExpression createCos(final int line, final int column, final Expression e) {
        if(!e.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The argument must be of the type Double.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.cos((Double) e.getResult());
            }
        };
    }

    @Override
    public ActionStatement createTurn(final int line, final int column, final Expression angle) {
        if (!angle.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The argument must be of the type Double.");

        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.turn(ProgramFactoryImpl.this.getWorm(), (Double) angle.getResult());
            }
        };
    }

    @Override
    public ActionStatement createMove(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.move(ProgramFactoryImpl.this.getWorm());
            }
        };
    }

    @Override
    public ActionStatement createJump(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.jump(ProgramFactoryImpl.this.getWorm());
            }
        };
    }

    @Override
    public ActionStatement createToggleWeap(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.toggleWeapon(ProgramFactoryImpl.this.getWorm());
            }
        };
    }

    @Override
    public ActionStatement createFire(final int line, final int column, final Expression yield) {
        if (!yield.getType().isAssignableFrom(Double.class))
            throw new IllegalTypeException(line, column, "The argument must be of the type Double.");

        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.fire(ProgramFactoryImpl.this.getWorm(), (int) ((double) yield.getResult()));
            }
        };
    }

    @Override
    public ActionStatement createSkip(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return true;
            }
        };
    }

    @Override
    public AssignmentStatement createAssignment(int line, int column, String variableName, Expression rhs) {
        //We cannot check for this error before executing the program since 
        //we don't have any acces to the globalList before the parser is done parsing.
        
        /*if(parser.getGlobals().containsKey(variableName)) {
            Variable variable = (Variable) parser.getGlobals().get(variableName);
            if(variable.isValidValueType(rhs))*/
                return new AssignmentStatement(parser, variableName, rhs);
           /* else
                throw new IllegalTypeException(line, column, "The type of the variable must be of type " + variable.getType());
        } else {
            throw new IllegalArgException(line, column, "The variable " + variableName + " doesn't exists");
        }*/
    }

    @Override
    public IfStatement createIf(int line, int column, Expression condition, Statement then, Statement otherwise) {
        if(!condition.getType().isAssignableFrom(Boolean.class))
            throw new IllegalTypeException(line, column, "The condition must be of type Boolean.");
        
        try {
            if(condition instanceof BooleanExpression)
                return new IfStatement((BooleanExpression) condition, then, otherwise);
            if(condition instanceof VariableExpression)
                return new IfStatement((VariableExpression) condition, then, otherwise);
        } catch(IllegalArgumentException ex) {
            throw new IllegalTypeException(line, column, ex.getMessage());
        }
        return null;
    }

    @Override
    public WhileStatement createWhile(int line, int column, Expression condition, Statement body) {
        if(!condition.getType().isAssignableFrom(Boolean.class))
            throw new IllegalTypeException(line, column, "The condition must be of type Boolean.");
        
        try {
            if(condition instanceof BooleanExpression)
                return new WhileStatement((BooleanExpression) condition, body);
            if(condition instanceof VariableExpression)
                return new WhileStatement((VariableExpression) condition, body);
        } catch(IllegalArgumentException ex) {
            throw new IllegalTypeException(line, column, ex.getMessage());
        }
        return null;
    }

    @Override
    public ForEachStatement createForeach(int line, int column, ForeachType type, String variableName, Statement body) {
        try {
            return new ForEachStatement(this, type, variableName, body);
        } catch(IllegalArgumentException ex) {
            throw new IllegalArgException(line, column, ex.getMessage());
        }
    }

    @Override
    public SequencedStatement createSequence(int line, int column, List<Statement> statements) {
        return new SequencedStatement(statements);
    }

    @Override
    public PrintStatement createPrint(int line, int column, Expression e) {
        return new PrintStatement(handler, e);
    }

    @Override
    public Variable<Double> createDoubleType() {
        Variable<Double> var = new Variable<>(Double.class);
        var.setValue(0.0);
        return var;
    }

    @Override
    public Variable<Boolean> createBooleanType() {
        Variable<Boolean> var = new Variable<>(Boolean.class);
        var.setValue(false);
        return var;
    }

    @Override
    public Variable<Entity> createEntityType() {
        return new Variable<>(Entity.class);
    }

    /**
     * Returns the worm associated with the program this factory builds.
     * @return 
     */
    public Worm getWorm() {
        return worm;
    }

}
