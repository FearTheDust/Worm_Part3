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
import worms.model.world.entity.Food;
import worms.model.world.entity.Worm;
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
    public void setProgramParser(ProgramParser parser) throws IllegalArgumentException {
        if(parser.getFactory() == this)
            this.parser = parser;
        else
            throw new IllegalArgumentException();
    }
    
    public ProgramParser getProgramParser() {
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
    private ProgramParser parser;
    
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
    public BooleanExpression createAnd(int line, int column, final Expression e1, final Expression e2) {
        if (!(e1 instanceof BooleanExpression) || !(e2 instanceof BooleanExpression))
            throw new IllegalTypeException(line, column, "An &&-expression must consist of atleast 2 BooleanExpressions");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((BooleanExpression) e1).getResult() && ((BooleanExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createOr(int line, int column, final Expression e1, final Expression e2) {
        if (!(e1 instanceof BooleanExpression) || !(e2 instanceof BooleanExpression))
            throw new IllegalTypeException(line, column, "An ||-expression must consist of atleast 2 BooleanExpressions");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((BooleanExpression) e1).getResult() || ((BooleanExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createNot(int line, int column, final Expression e) {
        if (!(e instanceof BooleanExpression))
            throw new IllegalTypeException(line, column, "The !-expression must consist of a BooleanExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return !((BooleanExpression) e).getResult();
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
                if(worm == null)
                    throw new IllegalStateException("The ProgramFactory has to have its worm set before we try to execute any statements created by the factory.");
                return worm;
            }
        };
    }

    @Override
    public DoubleExpression createGetX(int line, int column, final Expression e) {
        if(!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                //can throw NullPointerException/NotSupported/.. when necessary :)
                return ((EntityExpression) e).getResult().getPosition().getX();
            }
        };
    }

    @Override
    public DoubleExpression createGetY(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getPosition().getY();
            }
        };
    }

    @Override
    public DoubleExpression createGetRadius(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getRadius();
            }
        };
    }

    @Override
    public DoubleExpression createGetDir(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getAngle();
            }
        };
    }

    @Override
    public DoubleExpression createGetAP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxAP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getMaxAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetHP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getHP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxHP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getMaxHP();
            }
        };
    }

    @Override
    public BooleanExpression createSameTeam(int line, int column, final Expression e) {
        if(!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                    return getWorm().getTeam() == ((EntityExpression) e).getResult().getTeam();
            }
        };
    }

    @Override
    public EntityExpression createSearchObj(int line, int column, Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The parameter must be a double.");
        return new EntityExpression() {

            @Override
            public Entity getResult() {
                Worm worm = ProgramFactoryImpl.this.getWorm();
                return worm.getWorld().searchObject(worm.getPosition(), worm.getAngle());
            }
        };
    }

    @Override
    public BooleanExpression createIsWorm(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((EntityExpression) e).getResult() instanceof Worm;
            }
        };
    }

    @Override
    public BooleanExpression createIsFood(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException(line, column, "The parameter must be an entity.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((EntityExpression) e).getResult() instanceof Food;
            }
        };
    }

    @Override
    public Expression createVariableAccess(final int line, final int column, final String name) {
        //Is complex because we cannot call getGlobals while parsing...
        //TODO  problems with type (expression)
        return new Expression() {
            @Override
            public Object getResult() {
                final Variable variable = (Variable) getProgramParser().getGlobals().get("name");
                if(variable == null)
                    throw new IllegalArgException(line, column, name + " does not exist.");
                
                if(variable.getType() == Double.class) {
                    return new DoubleExpression() {
                        @Override
                        public Double getResult() {
                            return (Double) variable.getValue();
                        }
                        
                    };
                    
                } else if(variable.getType() == Boolean.class) {
                    return new BooleanExpression() {
                        @Override
                        public Boolean getResult() {
                            return (Boolean) variable.getValue();
                        }
                        
                    };
                } else if(variable.getType().isAssignableFrom(Entity.class)) {
                    return new EntityExpression() {
                        @Override
                        public Entity getResult() {
                            return (Entity) variable.getValue();
                        }
                    };
                }
                return null;
            }

            @Override
            public Class getType() {
                Variable variable = (Variable) getProgramParser().getGlobals().get("name");
                if (variable == null)
                    throw new IllegalArgException(line, column, name + " does not exist.");
                
                return variable.getType();
            }
        };
    }

    @Override
    public BooleanExpression createLessThan(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((DoubleExpression) e1).getResult()<((DoubleExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThan(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((DoubleExpression) e1).getResult()>((DoubleExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createLessThanOrEqualTo(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyLessThanOrEqualTo(((DoubleExpression) e1).getResult(),((DoubleExpression) e2).getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThanOrEqualTo(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyGreaterThanOrEqualTo(((DoubleExpression) e1).getResult(),((DoubleExpression) e2).getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createEquality(int line, int column, final Expression e1, final Expression e2) {
        if(!e1.getClass().isInstance(e2) && !e2.getClass().isInstance(e1))
            throw new IllegalTypeException(line, column, "Both expressions must have the same type.");
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (e1.getResult()==e2.getResult());
            }
        };
    }

    @Override
    public BooleanExpression createInequality(int line, int column, Expression e1, Expression e2) {
        return this.createNot(line, column, this.createEquality(line, column, e1, e2));
    }

    @Override
    public DoubleExpression createAdd(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()+((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSubtraction(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()-((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createMul(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()*((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createDivision(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "Both expressions must be of the type DoubleExpression.");
        
        // Division by zero will automatically throw an exception so we don't have to do it manually.
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()/((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSqrt(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The argument must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sqrt(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSin(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The argument must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sin(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createCos(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The argument must be of the type DoubleExpression.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.cos(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public Statement createTurn(int line, int column, final Expression angle) {
        if (!(angle instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The argument must be of the type DoubleExpression.");

        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.turn(worm, ((DoubleExpression) angle).getResult());
            }
        };
    }

    @Override
    public Statement createMove(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.move(worm);
            }
        };
    }

    @Override
    public Statement createJump(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.jump(worm);
            }
        };
    }

    @Override
    public Statement createToggleWeap(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.toggleWeapon(worm);
            }
        };
    }

    @Override
    public Statement createFire(int line, int column, final Expression yield) {
        if (!(yield instanceof DoubleExpression))
            throw new IllegalTypeException(line, column, "The argument must be of the type DoubleExpression.");

        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return handler.fire(worm, (int) ((double) ((DoubleExpression) yield).getResult()));
            }
        };
    }

    @Override
    public Statement createSkip(int line, int column) {
        return new ActionStatement() {
            @Override
            public boolean perform(Program program) {
                return true;
            }
        };
    }

    @Override
    public AssignmentStatement createAssignment(int line, int column, String variableName, Expression rhs) {
        //We cannot check for this error before executing the program since we don't have any acces to the globalList before the parser is done parsing.
        //:(
        //TODO Part of the globalVariableList question.
        
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
        if(!(condition instanceof BooleanExpression))
            throw new IllegalTypeException(line, column, "The condition must be of type BooleanExpression.");
        try {
            return new IfStatement((BooleanExpression) condition, then, otherwise);
        } catch(IllegalArgumentException ex) {
            throw new IllegalTypeException(line, column, ex.getMessage());
        }
    }

    @Override
    public WhileStatement createWhile(int line, int column, Expression condition, Statement body) {
        if(!(condition instanceof BooleanExpression))
            throw new IllegalTypeException(line, column, "The condition must be of type BooleanExpression.");
        
        try {
            return new WhileStatement((BooleanExpression) condition, body);
        } catch(IllegalArgumentException ex) {
            throw new IllegalTypeException(line, column, ex.getMessage());
        }
    }

    @Override
    public Statement createForeach(int line, int column, ForeachType type, String variableName, Statement body) {
        try {
            return new ForEachStatement(this, type, variableName, this.getProgramParser(), body);
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
    public Variable createDoubleType() {
        return new Variable(Double.class);
    }

    @Override
    public Variable createBooleanType() {
        return new Variable(Boolean.class);
    }

    @Override
    public Variable createEntityType() {
        return new Variable(Entity.class);
    }

    @Override
    public Worm getWorm() {
        return worm;
    }

}
